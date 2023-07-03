INSERT INTO `pizzas`(`price`, `created_at`, `description`, `name`, `photo`)VALUES (5.00, '2023-06-22 15:28', 'Classic pizza with tomato, mozzarella, and basil', 'Pizza Margherita', 'https://www.napolitoday.it/~media/horizontal-hi/59187489013853/pizza-14-13.jpg');

INSERT INTO `pizzas`(`price`, `created_at`, `description`, `name`, `photo`)VALUES (6.00, '2023-06-22 15:28', 'Delicious pizza with pepperoni, cheese, and tomato sauce', 'Pepperoni Pizza', 'https://it.rc-cdn.community.thermomix.com/recipeimage/u9e3gt48-e7ed9-378451-cfcd2-klj0wslw/ec3e9031-e924-4f4a-8e8e-db6198e271da/main/pizza-ai-peperoni.jpg');

INSERT INTO `pizzas`(`price`, `created_at`, `description`, `name`, `photo`)VALUES (6.50, '2023-06-22 15:28', 'Mouth-watering pizza topped with mushrooms, cheese, and truffle oil', 'Mushroom Truffle Pizza', 'https://www.colettanyc.com/wp-content/uploads/BV0A5472-copy-845x684.jpg');

INSERT INTO `pizzas`(`price`, `created_at`, `description`, `name`, `photo`)VALUES (7.00, '2023-06-22 15:28', 'Savory pizza with ham, pineapple, and cheese', 'Hawaiian Pizza', 'https://img.kidspot.com.au/52lezcwU/w643-h428-cfill-q90/kk/2015/03/hawaiian-pizza-recipe-605894-2.jpg');

INSERT INTO `pizzas`(`price`, `created_at`, `description`, `name`, `photo`)VALUES (8.50, '2023-06-22 15:28', 'Spicy pizza with jalapenos, chicken, and barbecue sauce', 'Spicy BBQ Chicken Pizza', 'https://www.italianfoodforever.com/wp-content/uploads/2009/10/blceleryporcinipizza.jpg');

INSERT INTO `pizzas`(`price`, `created_at`, `description`, `name`, `photo`)VALUES (9.00, '2023-06-22 15:28', 'Gourmet pizza with prosciutto, arugula, and Parmesan shavings',  'Prosciutto Arugula Pizza', 'https://www.oliveandmango.com/images/uploads/2021_08_fig_prosciutto_di_parma_PDO_Gorgonzola_and_arugula_pizza_with_hot_honey_1.jpg');

INSERT INTO `ingredients`(`name`) VALUES ('pomodoro');
INSERT INTO `ingredients`(`name`) VALUES ('mozzarella');
INSERT INTO `ingredients`(`name`) VALUES ('pepperoni');
INSERT INTO `ingredients`(`name`) VALUES ('fresh basil');
INSERT INTO `ingredients`(`name`) VALUES ('gorgonzola cheese');

INSERT INTO `roles`(`id`,`name`) VALUES (1, 'ADMIN');
INSERT INTO `roles`(`id`,`name`) VALUES (2, 'USER');

INSERT INTO `users`(`id`, `email`, `first_name`, `last_name`, `password`) VALUES (1, 'mariorossi@gmail.com','Mario','Rossi','{noop}mario');
INSERT INTO `users`(`id`, `email`, `first_name`, `last_name`, `password`) VALUES (2, 'laurabianchi@gmail.com','Laura','Bianchi','{noop}laura');

INSERT INTO `users_roles`(`user_id`, `roles_id`) VALUES ('1','1')
INSERT INTO `users_roles`(`user_id`, `roles_id`) VALUES ('2','2')