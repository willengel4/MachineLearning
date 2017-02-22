data = load('ex1data1.txt');
m = length(y);
y = data(:, 2);
X = [ones(m, 1), data(:,1)];
theta = pinv(X' * X) * X' * y
computeCost(X, y, theta);

