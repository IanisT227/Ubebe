clc
clear all
clf

N=[10:30:190, 5  12  16] ;    %n>=30
P=[0.02*ones(1,7),  0.5  0.3  0.8];    %p<=0.05

for i =  1 : 10    
  % or n=10 : 50 : 1000  or n = 1:2:100 or n = 1:5:100
    n = N(i);   
    p = P(i);
    Bino_pdf = binopdf(0 : n, n, p);
    plot(0 : n, Bino_pdf,'b')
    hold on; 
    %ylim([0 1])
    lambda=n*p;
    Poisson_pdf=poisspdf(0:n,lambda);
    plot(0:n,Poisson_pdf,'r--')
    hold off
    pause(0.3)
end
legend('Binomial', 'Poisson')