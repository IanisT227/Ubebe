pkg load statistics
clc
clear all

%  X ~ Bernoulli(p)=Bino(1,p)  <=>    X ~ (0     1)
%                                         (1-p   p)

% A random variable has a Bernoulli distribution of parameter p if it can 
% have only two possible outcomes: 'succes' (which occur with probability p)
% and 'failure' (which occur with probability 1-p)

% Example: Tossing a coin has only two outcomes: 'head' or 'tail'
% The random variable X that has value '1' when we get a 'head' and 
% value '0' otherwise has a Bernoulli distribution with parameter p=0.5 
%  (0.5 is the probability of getting a 'head' which we consider to be succes 

% Other example: Rolling a die and looking for a five
% The r.v. X which has the value '1' when we get a five (this is a 'succes')
% and value '0' when we get other numbers ('failure') has a Bernoulli 
% distribution with parameter p=1/6 (probability of succes)




%  First part: SIMULATION (mandatory)

% Now, giving a probability p, we want to simulate in Matlab/Octave the
% random process from real life. Hence we want to write a Matlab code that 
% randomly returnes a numerical value corresponding to the possible outcomes:
% succes (denoted by '1') or failure (denoted by '0') as they occur in 
% reality (with the same probability)

p=0.5; % let's take the first example: flipping a coin
%   to generate a random value corresponding to this process we can use
%   function 'binornd(1,p)' which gives me directly what I want
% But, since we want to make it more difficult we will use the function
%   'rand' which returnes randomly uniform distributed numbers
%    in the interval (0, 1) (the function unifrnd(0,1) does the same thing)
 
c=rand  % we have a random number in (0, 1) but we want X= 1 or 0  so....

if c < p
  X=1
else
  X=0
end
% Or, instead of using condition if, we can use directly the logical 
% operator < which returnes 1 if true and 0 if false :
X = (c<p)




% Second part: VERIFICATION (obtional)
%   Now we know for sure that we obtain only the values 0 and 1 but 
% we do not know what is the probability of occurrence of each one.
% One way to find out is to simulate the process a large number of times
% (N>100) and to check if '1' appears 50% (or 100*p% in general) of the 
% time and '0' the rest of the time.


N=1000;
Bernoulli=zeros(1,N); % we create a vector in which we keep the values
                 % simulated in those 1000 repetitions
for i=1:N % repeat the simulations 1000 times
  c=unifrnd(0,1);
  Bernoulli(i) = (c<=p) ; %we add in vector the value we get at step i
end

Frq_abs=hist(Bernoulli,0:1) %count the zeros and ones in vector 
Frq_rel=Frq_abs/N;          %compute the relative frequencies of each
disp('Estimated Bernoulli distribution:')
disp([0: 1; Frq_rel])  % display frequencies of simulated values
disp('Theoretical Bernoulli distribution:')
disp([0: 1; binopdf(0:1,1,p)]) %display the theoretical probabilities

bar(0:1,Frq_rel,'b') % plot frequencies of simulated values
hold on
bar(0:1,binopdf(0:1,1,p),'y') %plot the theoretical probabilities
legend('Estimated','Theoretical')
set(findobj('type','patch'),'facealpha',0.7);

% Change the value of p and N to see what happens