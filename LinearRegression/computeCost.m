function J = computeCost(X, y, theta)

m = length(y);

J = (1 / (2 * m)) * sumDifference(X, y, theta);

end
