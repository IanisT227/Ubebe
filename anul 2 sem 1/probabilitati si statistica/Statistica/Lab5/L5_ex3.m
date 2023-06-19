clc
clear all
pkg load statistics

Premium=[22.4 24.5 21.6 22.4 24.8 21.7 23.4 23.3 21.6 20.0];
Regular=[17.7 19.6 12.1 15.4 14.0 14.8 19.6 14.8 12.6 12.2];

conf_level=0.98; % confidence level      
alpha=1-conf_level   %significance level

% a) Confidence interval for difference of the theoretical means when 
%    the theoretical standard deviations are unknown but equal
m_1=mean(Premium); % sample mean for 1st vector
m_2=mean(Regular); %sample mean of 2nd vector
v_1=var(Premium); % sample variance
v_2=var(Regular); % sample variance
n_1=length(Premium);  % volume of 1st selection
n_2=length(Regular);  % volume of 2nd selection
t=tinv(1-alpha/2,n_1+n_2-2); % cuantile of order 1-alpha/2 for T(n1+n2-2)
s_p=((n_1-1)*v_1+(n_2-1)*v_2)/(n_1+n_2-2) ;  % pooled variance of the two samples
d_L=m_1-m_2-t*sqrt(s_p)*sqrt(1/n_1+1/n_2);    %left side of the interval
d_R=m_1-m_2+t*sqrt(s_p)*sqrt(1/n_1+1/n_2);    % right side of the interval
fprintf('a) CI for difference of means is (%4.2f,%4.2f)\n',d_L,d_R)

% b)Confidence interval for difference of the theoretical means when 
%    the theoretical standard deviations are unknown and different
c=(v_1/n_1)/(v_1/n_1+v_2/n_2);
n=1/(c^2/(n_1-1)+(1-c)^2/(n_2-1));
t=tinv(1-alpha/2,n); %cuantile of order 1-alpha/2 for T(n)
s_p=((n_1-1)*v_1+(n_2-1)*v_2)/(n_1+n_2-2); % pooled variance of the two samples
D_L=m_1-m_2-t*sqrt(s_p)*sqrt(v_1/n_1+v_2/n_2);  %left side of CI
D_R=m_1-m_2+t*sqrt(s_p)*sqrt(v_1/n_1+v_2/n_2);  % right side of CI
fprintf('b) CI for difference of means is (%4.2f,%4.2f)\n',D_L,D_R)


% c) Confidence interval for the ratio of the variances
f_L=finv(1-alpha/2,n_1-1,n_2-1); %cuantile of order 1-alpha/2 for F(n1-1,n2-1) (Fisher distribution)
f_R=finv(alpha/2,n_1-1,n_2-1); %cuantile of order alpha/2 for F(n1-1,n2-1) (Fisher distribution)
V_L=(1/f_L)*(v_1/v_2);  %left side 
V_R=(1/f_R)*(v_1/v_2);  %right side
fprintf('c) CI for ratio of variances (%4.2f,%4.2f)\n',V_L,V_R)
