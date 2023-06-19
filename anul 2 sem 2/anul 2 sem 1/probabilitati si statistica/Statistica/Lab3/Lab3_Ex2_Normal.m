clc
clear all
%{
N=16;
p=0.5;
for i=1:N
  n=5*i-3
  
  Bino_pdf=binopdf(0:n,n,p);
  subplot(4,4,i)
  plot(0:n,Bino_pdf)
  hold on
  m=n*p; s=sqrt(n*p*(1-p));
  Norm_pdf=normpdf(0:0.1:n,m,s);
  plot(0:0.1:n,Norm_pdf)
  hold off
end
%}
p = 0.5; 
for n = 1 : 3 : 100 % or n = 1:2:100, or n = 1:5:100
    Bino_pdf = binopdf(0 : n, n, p);
    plot(0 : n, Bino_pdf,'b')
    hold on
    ylim([0 0.8]);
    m=n*p; s=sqrt(n*p*(1-p));
    Norm_pdf=normpdf(0:0.1:n,m,s);
    plot(0:0.1:n,Norm_pdf,'r--')
    hold off
    pause(0.5)
end
