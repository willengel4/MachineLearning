function p = predict(Theta1, Theta2, X)
%PREDICT Predict the label of an input given a trained neural network
%   p = PREDICT(Theta1, Theta2, X) outputs the predicted label of X given the
%   trained weights of a neural network (Theta1, Theta2)

layer1 = [1; X(:)];
layer2 = sigmoid(Theta1 * layer1)
outputLayer = sigmoid(Theta2 * [1; layer2])

if outputLayer(1:1, 1:1) > 0.5
	p = 1;
else
	p = 0;
end

end
