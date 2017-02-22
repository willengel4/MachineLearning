function J = sumDifference(X, y, theta)

m = length(y);
n = length(X(1:1,:));

J = 0;

for r = 1:m
  Hr = theta' * X(r:r, 1:n)';
  Yr = y(r:r);
  J = J + ((Hr - Yr) ^ 2);
end

end
