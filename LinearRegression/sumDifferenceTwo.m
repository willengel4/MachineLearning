function J = sumDifferenceTwo(X, y, theta, f)

m = length(y);
n = length(X(1:1,:));

J = 0;

for r = 1:m
  Hr = theta' * X(r:r, 1:n)';
  Yr = y(r:r);
  Xf = X(r:r, f:f);
  J = J + ((Hr - Yr) * Xf);
end

end
