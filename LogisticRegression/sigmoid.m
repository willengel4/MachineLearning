function g = sigmoid(z)
%SIGMOID Compute sigmoid function
%   g = SIGMOID(z) computes the sigmoid of z.

% You need to return the following variables correctly 
g = zeros(size(z));

% ====================== YOUR CODE HERE ======================
% Instructions: Compute the sigmoid of each value of z (z can be a matrix,
%               vector or scalar).

width = columns(z);
height = rows(z(:,1:1));

for r = 1:height
	for c = 1:width
		g(r:r, c:c) = 1 / (1 + e ^ (z(r:r, c:c) * -1.0));
	end
end


% =============================================================

end
