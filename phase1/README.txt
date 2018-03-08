This program is run by the main method in the file Main.java

This program takes input in from the file "config/events.txt". The format to enter events, as well as sample events are
stored int the file, and the format can also be found at the bottom of this file for redundancy,

This file also uses the three configuration files "employees.txt", "menus.txt", and "ingredients.txt", these files
configure the lists of employees, the menus, and the ingredients that the restaurant has respectively. The format to
add lines to these files is also stored in those files, and is stored at the bottom of this file for redundancy.

#### events.txt ####

% Lines starting with a percentage sign are not read.
% The format of an event is as follows:
% <COMMAND> <EMPLOYEE_ID> <ARGUMENT_1> <ARGUMENT_2> ... <ARGUMENT_N>
%
% The available commands with their required arguments followed by an example is as follows:
%   ORDER: <TABLE_NUMBER> <MENU_NAME> <MENU_ITEM_NAME>
%          NO <INGREDIENT_NAME_1> ... <INGREDIENT_NAME_N>
%          ADD <INGREDIENT_NAME_N+1> ... <INGREDIENT_NAME_K>
%   E.g. "ORDER 0 10 lunch burger NO lettuce ketchup ADD mustard"
%
%   SEEN: <ORDER_NUMBER>
%   E.g. "SEEN 0 0"
%
%   READY: <ORDER_NUMBER>
%   E.g. "READY 0 0"
%
%   ACCEPT: <ORDER_NUMBER>
%   E.g. "ACCEPT 0 0"
%
%   REJECT: <ORDER_NUMBER> <REASON>
%   E.g. "REJECT 0 0 Wrong order"
%
%   REDO: <ORDER_NUMBER>
%   E.g. "REDO 0 0 Cold food"
%
%   BILL: <TABLE_NUMBER>
%   E.g. "BILL 0 0"
%
%   PAID: <TABLE_NUMBER>
%   E.g. "PAID 0 0"
%
%   RECEIVE: <INGREDIENT_NAME> <AMOUNT>
%   E.g. "RECEIVE 0 meat 100"
%
% Orders are automatically assigned zero-indexed incrementing numbers.

#### employees.txt ####

% Lines starting with a percentage sign are not read.
% The format of an employee creation is as follows:
% <EMPLOYEE_FIRST_NAME> <EMPLOYEE_LAST_NAME>
%
% Employees are automatically assigned zero-indexed incrementing IDs.

#### menus.txt ####

% Lines starting with a percentage sign are not read.
% The format of one menu is as follows:
% START <MENU>
% <MENU_ITEM_NAME_1> <PRICE>
% <INGREDIENT_NAME_1> ... <INGREDIENT_NAME_N>
% <ADDITION_NAME_1> ... <ADDITION_NAME_K>
%
% <MENU_ITEM_NAME_2>
% <INGREDIENT_NAME_1> ... <INGREDIENT_NAME_N>
% <ADDITION_NAME_1> ... <ADDITION_NAME_K>
%
% ...
% END

#### ingredients.txt ####

% Lines starting with a percentage sign are not read.
% The format of an event is as follows:
% <INGREDIENT_NAME> <ORDERING_THRESHOLD> <INITIAL_AMOUNT>