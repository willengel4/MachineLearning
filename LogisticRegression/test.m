function J = test(theta, X, y, lambda)

m = length(y); % number of training examples
n = columns(X);

temp = zeros(m);

for i = 1:m
	temp(i) = theta' * X(i:i, 1:n)';
end

temp

otherJ = 0;
for i = 1:rows(theta)
	otherJ = otherJ + (theta(i) ^ 2);
end
otherJ = ((otherJ * lambda) / (2 * m));

otherJ

for i = 1:m
	J = J + ((-1 * y(i) * log(sigmoid(temp(i)))) - ((1 - y(i)) * log(1 - sigmoid(temp(i)))))
end

J = J / m;

