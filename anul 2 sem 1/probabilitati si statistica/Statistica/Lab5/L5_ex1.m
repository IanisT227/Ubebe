pkg load statistics
clc
clear all


X=[20*ones(1,2), 21*ones(1,1), 22*ones(1,3), 23*ones(1,6), 24*ones(1,5),...
   25*ones(1,9), 26*ones(1,2), 27*ones(1,2)];
   
Y=[75*ones(1,3), 76*ones(1,2), 77*ones(1,2), 78*ones(1,5), 79*ones(1,8),...
   80*ones(1,8), 81*ones(1,1), 82*ones(1,1)];
% the values of X and Y corresponding to their frequencies

fprintf('The mean of X is %4.3f\n',mean(X))
fprintf('The mean of Y is %4.3f\n',mean(Y))
fprintf('The variance of X is %4.3f\n',var(X,1))
fprintf('The variance of Y is %4.3f\n',var(Y,1))
% these are the real variance (the variance of the entire population)
% and are different from the variance of a sample (selection of some data
% from the population) which is computed with var(X) or var(X,0)

fprintf('The covariance of X and Y is %4.3f\n',cov(X,Y,1))
%this computes the covarince of X and Y
% if you use MATLAB instead of Octave cov(X,Y,1) returnes the matrix:
%      (cov(X,X,1)=var(X,1)               cov(X,Y,1) )
%      (cov(Y,X,1)               cov(Y,Y,1)=var(Y,1) )
% and the value of interest is on the position (1,2)

 
corr_XY=corrcoef(X,Y)(1,2); %Pearson correlation coefficent
fprintf('The correlation coeff. of X and Y is %4.3f\n',corr_XY)
%corrcoef(X,Y) returnes the matrix:
%    (cov(X,X,1)/std(X,1)*std(X,1)       cov(X,Y,1)/std(X,1)*std(Y,1))
%    (cov(Y,X,1)/std(Y,1)*std(Y,1)       cov(Y,Y,1)/std(Y,1)*std(Y,1))
%the coefficent is on the position (1,2)

  
 



