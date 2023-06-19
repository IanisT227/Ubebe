clc
clear all


%  X~Bino(n,p)  <=>  X ~ (0     1     2    3   ...  n  )
%                        (P(0) P(1)  P(2) P(3) ... P(n))


% A random variable X has a Binomial distribution with parameters n and p
% if it can take n+1 values which represents the number of 'successes' 
% that are obtained after n repetitions of a Bernoulli process

% Example: Tossing a coin n=5 times: we can obtain 'head' (which we 
% consider succes) 0 times , 1, 2, 3, 4 times, or every time i.e. 5 times
% The random variable X that takes the values 0, 1, 2, ... n=5 which represents
% the number of 'heads' that occur when we toss a coin n=5 times has a
% Binomial distribution with parameters n=5 and p=0.5 (0.5 is still the
%  probability of getting a 'head' which we consider to be succes) 

% Other example: Rolling 3 dice and looking for a five
% The r.v. X which has the values 0, 1, 2 and 3 (the number of successes
% from 3 dice) has a Binomial distribution with parameters n=3 and p=1/6 
% (probability of succes)




%  First part: SIMULATION (mandatory)

% Now, giving a probability p and an integer n, we want to simulate
% in Matlab/Octave   n Bernoulli random processes from  real life. 
% Hence we want to write a Matlab code that randomly returnes
% a numerical value representing the possible outcomes:0, 1, 2,..., n
% and which corresponds to the number of successes that occur after those n reps 


p=0.5; n=5; % let's take the first example: flipping a coin 5 times
%   to generate a random value corresponding to this process we can use
%   function 'binornd(n,p)' which gives me directly what I want
% But, since we want to make it more difficult again we will use the 
% code that simulates the Bernoulli distribution and we run it 5 times

V=zeros(1,n); %we will insert in vector V the outcomes (0 or 1)
              % from the 5 tosses of the coin 
for i=1:n  %we simulates n=5 repetitions (tosses)
  c=rand;  
  if c < p
    X=1;       % we get a 1 (succes) or a 0 (failure) in each repetition
  else         
    X=0;
  end
  V(i)=X;   %we insert in vector V the outcome from step i
end
Bino=sum(V)   % finally we add all the values from vector V 
              % (in fact only the values '1' will be added )
              % and we obtain the number of o successes (denoted by ones)
              % that occur in a simulation of n=5 tosses of a coin
              
% Or, we can use directly the logical operator < (to avoid using if)

V=zeros(1,n); 
for i=1:n 
  c=rand; 
  X = (c<=p);
  V(i)=X;   
end
Bino=sum(V)








% Second part: VERIFICATION (obtional)

N=1000;
Binomial=zeros(1,N); % we create a vector in which we keep the values
                 % simulated in those 1000 repetitions of n=5 tosses of  coin
for j=1:N % repeat the simulation 1000 times
  
  V=zeros(1,n); %%%%%%%%%%%%%%%%%%%%%%%
  for i=1:n 
    c=rand ;
    X = (c<=p);   % This is the code used above for the simulation of one value
    V(i)=X;
  end
  Bino=sum(V);   %%%%%%%%%%%%%%%%%%%%%%%%%
  
  Binomial(j) = Bino ; %we add in Binomial the value we get at 
                       % simulation  i
end

Frq_abs=hist(Binomial,0:n) %count the values 0 1 2 3 4 5 in vector 
Frq_rel=Frq_abs/N;          %compute the relative frequencies 
disp('Estimated Binomial distribution:')
disp([0: n; Frq_rel])  % display frequencies of simulated values
disp('Theoretical Binomial distribution:')
disp([0: n; binopdf(0:n,n,p)]) %display the theoretical probabilities

bar(0:n,Frq_rel,'b') % plot frequencies of simulated values
hold on
bar(0:n,binopdf(0:n,n,p),'y') %plot the theoretical probabilities
legend('Estimated','Theoretical')
set(findobj('type','patch'),'facealpha',0.7);

% Change the value of n, p and N to see what happens


