function [J, grad] = costFunction(theta, X, y)
%COSTFUNCTION Compute cost and gradient for logistic regression
%   J = COSTFUNCTION(theta, X, y) computes the cost of using theta as the
%   parameter for logistic regression and the gradient of the cost
%   w.r.t. to the parameters.

% Initialize some useful values
m = length(y); % number of training examples


temp1 = (y .* -1) .* (log(sigmoid(X * theta)));
temp2 = (1 .- y) .* log(1.- (sigmoid(X * theta)));
temp3 = (temp1 - temp2) + otherJ;

s = sum(temp3);
J = s / m;

grad = (1/m) * (X' * (sigmoid(X * theta) - y));

% =============================================================

end
