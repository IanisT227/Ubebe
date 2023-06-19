clc
clear all
pkg load statistics

Data=[7  7  4  5  9  9; 
      4 12  8  1  8  7;
      3 13  2  1 17  7;
     12  5  6  2  1 13;
     14 10  2  4  9 11;
      3  5 12  6  10 7]
      
conf_level=0.99; % confidence level      
alpha=1-conf_level;   %significance level

% a)  Confidence interval for theoretical mean when standard deviation is known

sigma=5;  % theoretical (real) standard deviation  (of the population)
m_s=mean(Data(:));  %mean of the sample (selected data)
n=length(Data(:));  % volume of the sample
z=norminv(1-alpha/2,0,1);  %cuantile of order 1-alpha/2 for N(0,1) (standard normal distribution)
m_L=m_s-sigma/sqrt(n)*z;  % left edge 
m_R=m_s+sigma/sqrt(n)*z;  % right edge
fprintf('The confidence interval for mean is (%4.2f,%4.2f)\n',m_L,m_R)

%b) Confidence interval for theoretical mean when standard deviation is unknown

s=std(Data(:)); % standard deviation of the sample (selected data)
t=tinv(1-alpha/2,n-1);  %cuantile of order 1-alpha/2 for T(n-1) (Student distribution with parameter n-1)
M_L=m_s-s/sqrt(n)*t;  
M_R=m_s+s/sqrt(n)*t;
fprintf('The confidence interval for mean in the second case is (%4.2f,%4.2f)\n',M_L,M_R)


%c)  Confidence interval for theoretical variance 
v=var(Data(:));  % variance of sample (selection)
chi_L=chi2inv(1-alpha/2,n-1);  %cuantile of order 1-alpha/2 for Chi2(n-1) (Chi2 distribution)
chi_R=chi2inv(alpha/2,n-1);    %cuantile of order alpha/2 for Chi2(n-1)
V_L=(n-1)*v/chi_L;
V_R=(n-1)*v/chi_R;
fprintf('The confidence interval for variance is (%4.2f,%4.2f)\n',chi_L,chi_R)