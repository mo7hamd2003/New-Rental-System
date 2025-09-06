-- SQL Script to add the addedByUserId column to the Vehicle table
-- Run this in your SQL Server Management Studio or database tool

USE [YourDatabaseName]; -- Replace with your actual database name

-- Add the new column to track who added each vehicle
ALTER TABLE Vehicle
ADD addedByUserId INT NULL;

-- Optional: Add a foreign key constraint if you have a Users table
-- ALTER TABLE Vehicle
-- ADD CONSTRAINT FK_Vehicle_User FOREIGN KEY (addedByUserId) REFERENCES Users(id);

-- Optional: Update existing records to have a default user ID (if you know one)
-- UPDATE Vehicle SET addedByUserId = 1 WHERE addedByUserId IS NULL; -- Replace 1 with actual admin user ID

PRINT 'Vehicle tracking column added successfully!';