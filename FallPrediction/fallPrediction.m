data = load('data2.txt');
X = data(:, 1:20);
y = data(:, 21:21);

options = optimset('MaxIter', 50);
input_layer_size  = 20;
hidden_layer_size = 20;
num_labels = 1;  
lambda = 1;
INIT_EPSILON = 0.12;
initial_theta1 = rand(20, 21) * (2 * INIT_EPSILON) - INIT_EPSILON;
initial_theta2 = rand(1, 21) * (2 * INIT_EPSILON) - INIT_EPSILON;

initial_nn_params = [ initial_theta1(:); initial_theta2(:)];

costFunction = @(p) nnCostFunction(p, ...
                                   input_layer_size, ...
                                   hidden_layer_size, ...
                                   num_labels, X, y, lambda);

[nn_params, cost] = fmincg(costFunction, initial_nn_params, options);

Theta1 = reshape(nn_params(1:hidden_layer_size * (input_layer_size + 1)), ...
                 hidden_layer_size, (input_layer_size + 1));

Theta2 = reshape(nn_params((1 + (hidden_layer_size * (input_layer_size + 1))):end), ...
                 num_labels, (hidden_layer_size + 1));

fall_input = load('fall_input.txt');

predict(Theta1, Theta2, fall_input);
