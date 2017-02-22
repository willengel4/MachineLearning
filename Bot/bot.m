data = load('data.txt');
X = data(:, 1:401);
y = data(:, 402:402);

options = optimset('MaxIter', 50);
input_layer_size  = 401;
hidden_layer_size = 20;
num_labels = 6;
lambda = 1;
INIT_EPSILON = 0.12;
initial_theta1 = rand(20, 402) * (2 * INIT_EPSILON) - INIT_EPSILON;
initial_theta2 = rand(6, 21) * (2 * INIT_EPSILON) - INIT_EPSILON;

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

countRight = 0;
for i = 1:rows(X)
	x = X(i:i, 1:input_layer_size);
	p = predict(Theta1, Theta2, x);
	if p == y(i)
		countRight = countRight + 1;
	else
		y(i);
		p;
	end

countRight

tempTheta1 = Theta1'(:);
tempTheta2 = Theta2'(:);

save theta1.txt tempTheta1 -ascii
save theta2.txt tempTheta2 -ascii

end
