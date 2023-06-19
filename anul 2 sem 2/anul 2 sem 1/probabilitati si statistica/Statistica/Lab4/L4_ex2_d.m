
clc
clear all



%  X~Nbin(n,p)  <=>     X ~ (0     1     2     3   ...   k      ...  )
%                           (P(0) P(1)  P(2)  P(3) ...  P(k)    ...  )


% A random variable X has a Nbinomial distribution with parameters n and p
% if it is a sum of n independent random variables that have 
% Geometrical distribution with parameter p 


p=0.3; n=5;
V=zeros(n,1);
for j=1:n
  while unifrnd(0,1)>p
    V(j)++;
  end
end
X=sum(V)

%  Verification
N=1000; k=20; 
M=zeros(n,N);
for i=1:N
  for j=1:n
    while unifrnd(0,1)>p
     M(j,i)++;
    end
  endfor
end
NB=sum(M);

Frq_abs=hist(NB,0:k+1);
Frq_rel=Frq_abs(1:k+1)/N;
%{
disp('Estimated Pascal distribution:')
disp([0: k; Frq_rel])
disp('Theoretical Pascal distribution:')
disp([0:k; geopdf(0:k,p)])
%}
bar(0:k,Frq_rel,'b')
hold on
bar(0:k,nbinpdf(0:k,n,p),'y')
legend('Estimated','Theoretical')
set(findobj('type','patch'),'facealpha',0.7);