-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema BookStore
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema BookStore
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `BookStore` ;
USE `BookStore` ;

-- -----------------------------------------------------
-- Table `BookStore`.`Publisher`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BookStore`.`Publisher` (
  `name` VARCHAR(45) NOT NULL,
  `address` VARCHAR(45) NOT NULL,
  `telephone` CHAR(11) NOT NULL,
  PRIMARY KEY (`name`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BookStore`.`Category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BookStore`.`Category` (
  `type` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`type`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BookStore`.`Book`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BookStore`.`Book` (
  `ISBN` INT NOT NULL AUTO_INCREMENT,
  `title` VARCHAR(45) NOT NULL,
  `publisher` VARCHAR(45) NOT NULL,
  `publication_year` VARCHAR(45) NOT NULL,
  `price` INT NOT NULL,
  `category` VARCHAR(45) NOT NULL,
  `quantity` INT NOT NULL,
  `threshold` INT NOT NULL,
  PRIMARY KEY (`ISBN`),
  INDEX `fk_Book_3_idx` (`title` ASC),
  INDEX `fk_Book_2_idx` (`category` ASC),
  INDEX `fk_Book_1_idx` (`publisher` ASC),
  CONSTRAINT `fk_Book_1`
    FOREIGN KEY (`publisher`)
    REFERENCES `BookStore`.`Publisher` (`name`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_Book_2`
    FOREIGN KEY (`category`)
    REFERENCES `BookStore`.`Category` (`type`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `BookStore`.`Author`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BookStore`.`Author` (
  `ISBN` INT NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`ISBN`, `name`),
  INDEX `fk__2_idx` (`publisher` ASC),
  CONSTRAINT `fk_Author_1`
    FOREIGN KEY (`ISBN`)
    REFERENCES `BookStore`.`Book` (`ISBN`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BookStore`.`User`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BookStore`.`User` (
  `user_id` INT NOT NULL AUTO_INCREMENT,
  `user_name` VARCHAR(45) NOT NULL UNIQUE,
  `password` VARCHAR(45) NOT NULL,
  `lastName` VARCHAR(45) NOT NULL,
  `firstName` VARCHAR(45) NOT NULL,
  `mail` VARCHAR(45) NOT NULL UNIQUE,
  `phone` CHAR(11) NOT NULL,
  `shipping_address` VARCHAR(45) NOT NULL,
  `user_type` TINYINT(1) NOT NULL,
  PRIMARY KEY (`user_id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BookStore`.`customer_order`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BookStore`.`customer_order` (
  `order_id` INT NOT NULL AUTO_INCREMENT,
  `user_id` INT NOT NULL,
  `date` DATE NOT NULL,
  `price` INT NOT NULL,
  PRIMARY KEY (`order_id`),
  INDEX `fk_customer_order_1_idx` (`user_id` ASC),
  CONSTRAINT `fk_customer_order_1`
    FOREIGN KEY (`user_id`)
    REFERENCES `BookStore`.`User` (`user_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BookStore`.`Book_order`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BookStore`.`Book_order` (
  `book_order_id` INT NOT NULL auto_increment,
  `ISBN` INT NOT NULL,
  `quantity` INT NOT NULL,
  PRIMARY KEY (`book_order_id`),
  INDEX `fk_Book_order_1_idx` (`ISBN` ASC),
  CONSTRAINT `fk_Book_order_1`
    FOREIGN KEY (`ISBN`)
    REFERENCES `BookStore`.`Book` (`ISBN`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BookStore`.`Items`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `BookStore`.`Items` (
  `order_id` INT NOT NULL,
  `ISBN` INT NOT NULL,
  `quantity` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`order_id`, `ISBN`),
  INDEX `fk_Items_2_idx` (`ISBN` ASC),
  CONSTRAINT `fk_Items_1`
    FOREIGN KEY (`order_id`)
    REFERENCES `BookStore`.`customer_order` (`order_id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_Items_2`
    FOREIGN KEY (`ISBN`)
    REFERENCES `BookStore`.`Book` (`ISBN`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- confirming an order 
DELIMITER //
CREATE TRIGGER  order_recieved  BEFORE DELETE ON Book_order FOR EACH ROW
BEGIN
	UPDATE Book 
    SET quantity=quantity+old.quantity
    WHERE Book.ISBN=old.ISBN;
END;
// DELIMITER ;
-- adding new book
DELIMITER //
CREATE PROCEDURE Add_new_book(ISBN INT,title VARCHAR(45),publisher VARCHAR(45),publication_year DATE,price INT,category VARCHAR(45),quantity INT,threshold INT)
BEGIN
		INSERT INTO Book
        VALUES (ISBN,title,publisher,publication_year,price,category,quantity,threshold);
END
// DELIMITER ;

-- checking if quantity less than 0
DELIMITER //
CREATE TRIGGER  limit_book_quantity  BEFORE UPDATE ON Book FOR EACH ROW
BEGIN
	IF(New.quantity < 0) THEN
		SIGNAL SQLSTATE '45000'
		SET MESSAGE_TEXT = 'Quantity in Stock is not sufficient';
	END IF;
END;
// DELIMITER ;

-- searching
DELIMITER //
CREATE PROCEDURE search_by_ISBN(id INT)
BEGIN
		SELECT *
        FROM Book
        WHERE ISBN=id;
END
// DELIMITER ;

DELIMITER //
CREATE PROCEDURE search_by_title(title_name VARCHAR(45))
BEGIN
		SELECT *
        FROM Book
        WHERE title=title_name;
END
// DELIMITER ;

DELIMITER //
CREATE PROCEDURE search_by_category(cat VARCHAR(45), pageNum INT)
BEGIN
		SELECT *
        FROM Book
        WHERE category=cat
        LIMIT 20
        OFFSET pageNum;
END
// DELIMITER ;

DELIMITER //
CREATE PROCEDURE search_by_publisher(publisher_name VARCHAR(45), pageNum Int)
BEGIN
		SELECT *
        FROM Book
        WHERE publisher=publisher_name
        LIMIT 20
        OFFSET pageNum;
END
// DELIMITER ;

DELIMITER //
CREATE PROCEDURE search_by_author(author_name VARCHAR(45), pageNum Int)
BEGIN
		SELECT Book.*,Author.name AS author_name
        FROM Author,Book
        WHERE name=author_name
        LIMIT 20
        OFFSET pageNum;
END
// DELIMITER ;

DELIMITER //
CREATE PROCEDURE search_by_price(price Int, pageNum INT)
BEGIN
		SELECT *
        FROM Book
        WHERE price=price
        LIMIT 20
        OFFSET pageNum;
END
// DELIMITER ;

-- make an order if quantity less than threshold
DELIMITER //
CREATE TRIGGER  threshold_order  AFTER UPDATE ON Book FOR EACH ROW
BEGIN
	IF(New.quantity < New.threshosearch_by_authorld) THEN
		INSERT INTO Book_order
        VALUES(DEFAULT,New.ISBN,New.threshold);
	END IF;
END;
// DELIMITER ;
DELIMITER //
CREATE TRIGGER  reduce_quantity  AFTER INSERT ON Items FOR EACH ROW
BEGIN
	UPDATE Book
	SET Book.quantity=Book.quantity-new.quantity
	WHERE Book.ISBN=new.ISBN;
END
// DELIMITER ;



