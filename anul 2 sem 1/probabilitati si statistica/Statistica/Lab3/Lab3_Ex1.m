clc
pkg load statistics
clear all
alpha = 0.2;  %input('alpha = ');
beta = 0.3;   %input('beta = ');
option = input('Choose option: normal, student, Chi2, Fischer','s');
switch option
  case 'normal'
    %  X~Norm(m,s)    normal distribution
    disp('Normal distribution')
    mu=input('mu=');
    sigma=input('sigma=');
    Pa1=normcdf(0,mu,sigma);
    Pa2=1-normcdf(0,mu,sigma);
    Pb1=normcdf(1,mu,sigma) - normcdf(-1,mu,sigma);
    Pb2=normcdf(-1,mu,sigma) + (1-normcdf(1,mu,sigma));
    Pc=norminv(alpha,mu,sigma);
    Pd=norminv(1-beta,mu,sigma);

  case 'student'
    %  X~T(n)    student distribution
    disp('Student distribution')
    n=input('n=')
    Pa1=tcdf(0,n);
    Pa2=1-tcdf(0,n);
    Pb1=tcdf(1,n) - tcdf(-1,n);
    Pb2=tcdf(-1,n) + (1-tcdf(1,n));
    Pc=tinv(alpha,n);
    Pd=tinv(1-beta,n);

  case 'Chi2'
    %  X~Chi2(k)    Chi2 distribution
    disp('Chi2 distribution')
    k=input('k=')
    Pa1=chi2cdf(0,k);
    Pa2=1-chi2cdf(0,k);
    Pb1=chi2cdf(1,k) - chi2cdf(-1,k);
    Pb2=chi2cdf(-1,k) + (1-chi2cdf(1,k));
    Pc=chi2inv(alpha,k);
    Pd=chi2inv(1-beta,k);

  case 'Fischer'
    %  X~F(m,n)    Fisher distribution
    disp('Fisher distribution')
    m=input('m='); n=input('n=');
    Pa1=fcdf(0,m,n);
    Pa2=1-fcdf(0,m,n);
    Pb1=fcdf(1,m,n) - fcdf(-1,m,n);
    Pb2=fcdf(-1,m,n) + (1-fcdf(1,m,n));
    Pc=finv(alpha,m,n);
    Pd=finv(1-beta,m,n);
  otherwise
    fprintf('Wrong option!')
end

% Displaying the results.
fprintf('First probability in part a) is: %1.5f \n', Pa1)
fprintf('Second probability in part a) is: %1.5f \n', Pa2)
fprintf('First probability in part b) is: %1.5f \n', Pb1)
fprintf('Second probability in part b) is: %1.5f \n', Pb2)
fprintf('Answer in part c) x_alpha is: %1.5f \n', Pc)
fprintf('Answer in part d) x_beta is: %1.5f \n', Pd)