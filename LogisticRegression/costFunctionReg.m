function [J, grad] = costFunctionReg(theta, X, y, lambda)

% Initialize some useful values
m = length(y); % number of training examples

oj = sum(theta .* theta) * (lambda / (2 * m));

temp1 = (y .* -1) .* (log(sigmoid(X * theta)));
temp2 = (1 .- y) .* log(1.- (sigmoid(X * theta)));
temp3 = (temp1 - temp2) + oj;

s = sum(temp3);
J = s / m;

grad = (1/m) * (X' * (sigmoid(X * theta) - y));
grad2 = grad + ((lambda / m) * theta);
grad2(1) = grad(1);
grad = grad2;

end
