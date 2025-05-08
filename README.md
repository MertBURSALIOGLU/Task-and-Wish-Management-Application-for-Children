# Task and Wish Management Application for Children

A Java console-based application where children can:
- Track daily/weekly tasks assigned by parents/teachers
- Earn coins based on task performance
- Submit wishes (items/activities) for approval
- Gain levels and unlock new rewards

## Features
- Task and Wish Management
- Console Command Processing
- File Input/Output (`Tasks.txt`, `Wishes.txt`, `Commands.txt`)
- Level-Based Wish Approval System

## Getting Started
1. Clone the repo
2. Place your `Tasks.txt`, `Wishes.txt`, and `Commands.txt` files in the root directory
3. Compile and run `Main.java`

## Improvements As a Group

1. The Child class was updated to use append mode when writing tasks and wishes to files. This prevents overwriting existing data and ensures that new entries are added efficiently to the end of the file.
2. The implementation follows the class structure agreed upon by the team.
3. The wishlevel() method is used to assign a required level to a wish. However, it did not trigger an approval check after updating the level. To address this, the approveEligibleWishes() method was added to ensure immediate validation. This allows eligible wishes to be automatically approved without waiting for a coin-based level update.
4. 

