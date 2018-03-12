This program is run by the main method in the file Main.java

This program takes input in from the file "config/events.txt". The format to enter events, as well as sample events are
stored int the file, and the format can also be found at the bottom of this file for redundancy,

This program also uses the three configuration files "config/employees.txt", "config/menus.txt", and
"config/ingredients.txt", these files configure the lists of employees, the menus, and the ingredients that the
restaurant has respectively. The format to add lines to these files is also stored in those files, and is stored at the
bottom of this file for redundancy.

Output for the program goes to "log.txt", and shows how each event is processed, as it is processed. As well as to
"requests.txt", and when orders are ready, or the bill is requested, it is logged, as well as printed to screen.

What follows is the intended order of events for our events.txt file, should someone wish to create their own
events file. Then, the format intended for each of the events.txt, employees.txt, menus.txt and ingredients.txt.

#### Intended Order of Events ####

Suppose food is ordered:
ORDER                   (An order is originally placed)
SEEN                    (This order is presented to the kitchen/chef)
READY                   (This order has been prepared and is ready for serving)

READY can lead to three different events:
ACCEPT                  (This order has been received and all is well)
REDO                    (There is a problem with this order. This problem is given to the kitchen to fix in a new ORDER)
REJECT                  (There is a problem with this order so catastrophic that the customer doesn't want it anymore)

CONFIRMED adds this menu item to the bill, and then this table can reach BILL:
BILL                    (The bill has been presented to the table, as they are done eating)
PAID                    (The bill has been paid and the table is now clear for more customers)

There is one more event that is not a part of this flow:
RECEIVE                 (Some ingredient has been delivered, and is to be added to the inventory)

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