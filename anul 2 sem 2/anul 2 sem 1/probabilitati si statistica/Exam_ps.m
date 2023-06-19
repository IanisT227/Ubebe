clc
clear all
pkg load statistics

Steel= [4.6 0.7 4.2 1.9 4.8 6.1 4.7 5.5 5.4]
Glass= [2.5 1.3 2.0 1.8 2.7 3.2 3.0 3.5 3.4]

conf_level=0.99; % confidence level
alpha=1-conf_level; %significance level ( 1%) 

fprintf('alpha is %1d\n', alpha);


%a) At the 1% significance level, do the population variances seem to differ?

% we test the opposite hypothesis ( if the variances are equal) 
%  The Hypothesis: the ratio R = var_1/var_2 = 1 <=> var_1 = var_2
% H0:  R (the ratio)  =  1
% H0:  r (the ratio) =/= 1  (two-tailed test)


[H, PVAL, CI, STATS] = vartest2(Steel, Glass,...
                         'alpha',alpha, 'tail','both');
                         
n1=length(Steel);      
n2=length(Glass);       
t_1=finv(alpha/2,n1-1,n2-1);   %cuantile of order alpha/2 for F(n1-1,n2-1)
t_2=finv(1-alpha/2,n1-1,n2-1); %cuantile of order 1-alpha/2 for F(n1-1,n2-1)
RR=[-inf,t_1,t_2,inf];         % rejection region
fprintf('H is %1d\n',H) ;      % if H=0 we accept H0 but if H=1 we reject H0
fprintf('TS_0 is %6.4f\n',STATS.fstat); % observed value of the test statistic F 
fprintf('RR is (%6.4f,%6.4f)U(%6.4f,%6.4f)\n',RR); % rejection region
fprintf('P-value is %6.4f\n',PVAL); %P-value

if H==0
  disp('The variances do not differ')
else
  disp('The variances are different')
end


%b) find a 99% confidence interval for the difference of the average heat losses

m_s= mean(Steel); % average for steel 
m_g= mean(Glass); % average for glass

fprintf('Mean for steel is %1d\n',m_s) ;
fprintf('Mean for glass is %1d\n',m_g) ;

% when the standard deviations are equal
% -- we use this, because the variances do not differ


v_1=var(Steel); % sample variance
v_2=var(Glass); % sample variance
n_1=length(Steel);  % volume of 1st selection
n_2=length(Glass);  % volume of 2nd selection
t=tinv(1-alpha/2,n_1+n_2-2); % cuantile of order 1-alpha/2 for T(n1+n2-2)
s_p=((n_1-1)*v_1+(n_2-1)*v_2)/(n_1+n_2-2) ;  % pooled variance of the two samples
d_L=m_s-m_g-t*sqrt(s_p)*sqrt(1/n_1+1/n_2);    %left side of the interval
d_R=m_s-m_g+t*sqrt(s_p)*sqrt(1/n_1+1/n_2);    % right side of the interval
fprintf('CI for difference of means is (%4.2f,%4.2f)\n',d_L,d_R)


%if the standard deviations would be unequal

c=(v_1/n_1)/(v_1/n_1+v_2/n_2);
n=1/(c^2/(n_1-1)+(1-c)^2/(n_2-1));
t=tinv(1-alpha/2,n); %cuantile of order 1-alpha/2 for T(n)
s_p=((n_1-1)*v_1+(n_2-1)*v_2)/(n_1+n_2-2); % pooled variance of the two samples
D_L=m_s-m_g-t*sqrt(s_p)*sqrt(v_1/n_1+v_2/n_2);  %left side of CI
D_R=m_s-m_g+t*sqrt(s_p)*sqrt(v_1/n_1+v_2/n_2);  % right side of CI
fprintf('CI for difference of means is (%4.2f,%4.2f)\n',D_L,D_R)





