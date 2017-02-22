function [J grad] = nnCostFunction(nn_params, ...
                                   input_layer_size, ...
                                   hidden_layer_size, ...
                                   num_labels, ...
                                   X, y, lambda)
%NNCOSTFUNCTION Implements the neural network cost function for a two layer
%neural network which performs classification
%   [J grad] = NNCOSTFUNCTON(nn_params, hidden_layer_size, num_labels, ...
%   X, y, lambda) computes the cost and gradient of the neural network. The
%   parameters for the neural network are "unrolled" into the vector
%   nn_params and need to be converted back into the weight matrices. 
% 
%   The returned parameter grad should be a "unrolled" vector of the
%   partial derivatives of the neural network.
%

% Reshape nn_params back into the parameters Theta1 and Theta2, the weight matrices
% for our 2 layer neural network
Theta1 = reshape(nn_params(1:hidden_layer_size * (input_layer_size + 1)), hidden_layer_size, (input_layer_size + 1));

Theta2 = reshape(nn_params((1 + (hidden_layer_size * (input_layer_size + 1))):end), num_labels, (hidden_layer_size + 1));

% Setup some useful variables
m = size(X, 1);
k = num_labels;         
% You need to return the following variables correctly 
J = 0;
Theta1_grad = zeros(size(Theta1));
Theta2_grad = zeros(size(Theta2));

oj = 0;

for i = 1:rows(Theta1)
	for j = 2:columns(Theta1)
		oj = oj + (Theta1(i:i,j:j) * Theta1(i:i,j:j));
	end
end

for i = 1:rows(Theta2)
	for j = 2:columns(Theta2)
		oj = oj + (Theta2(i:i,j:j) * Theta2(i:i,j:j));
	end
end

oj = oj * (lambda / (2 * m));

for i = 1:m
	layer2 = [1; sigmoid(Theta1 * [1; X(i,:)'])];
	layer3 = sigmoid(Theta2 * layer2);
	tempY = zeros(k, 1);
	tempY(1) = y(i);

	temp1 = (tempY .* -1) .* (log(layer3));
	temp2 = (1 .- tempY) .* log(1 .- (layer3));
	temp3 = (temp1 - temp2);
	s2 = sum(temp3);
	J = J + s2;

	outputError = layer3 .- tempY;
	hiddenError = (Theta2' * outputError) .* sigmoidGradient([1; Theta1 * [1; X(i,:)']]);

	Theta2_grad = Theta2_grad + (outputError * layer2'); 
	Theta1_grad = Theta1_grad + (hiddenError(2:end) * [1 X(i,:)]);
end

J = J / m;
J = J + oj;

for i = 1:rows(Theta2_grad)
	for j = 1:columns(Theta2_grad)
		if j == 1
			Theta2_grad(i,j) = Theta2_grad(i,j) / m;
		else
			Theta2_grad(i,j) = (Theta2_grad(i,j) / m) + ((lambda / m) * Theta2(i,j));
		end
	end
end

for i = 1:rows(Theta1_grad)
	for j = 1:columns(Theta1_grad)
		if j == 1
			Theta1_grad(i,j) = Theta1_grad(i,j) / m;
		else
			Theta1_grad(i,j) = (Theta1_grad(i,j) / m) + ((lambda / m) * Theta1(i,j));
		end
	end
end

% ====================== YOUR CODE HERE ======================
% Instructions: You should complete the code by working through the
%               following parts.
%
% Part 1: Feedforward the neural network and return the cost in the
%         variable J. After implementing Part 1, you can verify that your
%         cost function computation is correct by verifying the cost
%         computed in ex4.m
%
% Part 2: Implement the backpropagation algorithm to compute the gradients
%         Theta1_grad and Theta2_grad. You should return the partial derivatives of
%         the cost function with respect to Theta1 and Theta2 in Theta1_grad and
%         Theta2_grad, respectively. After implementing Part 2, you can check
%         that your implementation is correct by running checkNNGradients
%
%         Note: The vector y passed into the function is a vector of labels
%               containing values from 1..K. You need to map this vector into a 
%               binary vector of 1's and 0's to be used with the neural network
%               cost function.
%
%         Hint: We recommend implementing backpropagation using a for-loop
%               over the training examples if you are implementing it for the 
%               first time.
%
% Part 3: Implement regularization with the cost function and gradients.
%
%         Hint: You can implement this around the code for
%               backpropagation. That is, you can compute the gradients for
%               the regularization separately and then add them to Theta1_grad
%               and Theta2_grad from Part 2.
%

% -------------------------------------------------------------

% =========================================================================

% Unroll gradients
grad = [Theta1_grad(:) ; Theta2_grad(:)];


end
