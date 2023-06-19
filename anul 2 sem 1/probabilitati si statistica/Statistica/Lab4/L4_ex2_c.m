
clc
clear all

%  X~Geo(p)  <=>     X ~ (0     1     2     3   ...   k      ...  )
%                        (P(0) P(1)  P(2)  P(3) ...  P(k)    ...  )


% A random variable X has a Geometrical distribution with parameter p
% if it can take positive integer values which represents the number of 
% 'failures' that occur before the first 'succes' is obtained
% when we simulate a Bernoulli process in which the probability of succes is p

% Example: Tossing a coin n=5 times: before we obtain first 'head' 
% (which we consider succes), 'tail' can occur 0 times , 1, 2, 3, 4 times, 
% or even more (the number goes to infinity)
% The random variable X that takes the values 0, 1, 2, ... k ...  which
% represents the number of 'tails' (failures) that occur before we get the 
% first 'head' when we toss a coin has a Geometrical distribution with 
% parameter p=0.5 (0.5 is still the probability of getting a 'head' which
%  we consider to be succes) 

% Other example: Rolling a die and looking for a 'five'
% The r.v. X which has the values 0, 1, 2 ... (the number of failures
% before first 'five') has a Geometrical distribution with parameter p=1/6 
% (probability of succes)


%  First part: SIMULATION (mandatory)

% Now, giving a probability p , we want to simulate in Matlab/Octave   
% a Bernoulli random process from  real life and we want to return the
% number of simulation needed before we achieve the first succes. 
% Hence we want to write a Matlab code that randomly returnes
% a numerical value corresponding to the possible outcomes: succes (denoted 
% by '1') or failure (denoted by '0'), to repeat the process until we get
% the first 'succes', and then to return the number of failed repetitions


p=0.5;  % let's take the first example: flipping a coin 
%   to generate a random value corresponding to this process we can use
%   function 'geornd(p)' which gives me directly what I want
% But, since we want to make it more difficult again we will use the 
% code that simulates the Bernoulli distribution and we run it until 
% we get the first '1'

%  Generating  X~Geo(p)
Geo=0;    
c=rand; 
X = (c<=p); %we get fist outcome 
while X != 1  % if it is a failure (different from 1) we continue to 
  c=rand;     % repeat the process until we get the first succes
  X = (c<=p);
  Geo++;        % and we keep counting the number of repetitions (that fails)
end             
Geo          % Geo represents the total number of failures obtained 
             % in a session of random simulations

                
                
                
                
                
                
% Second part: VERIFICATION (obtional)
%   We want so see if our results corresponds to the theoretical ones
%  Hence we repeat the whole process described above N=1000 times
%  and we get 1000 random values for Geo (not unique) and compare 
%  the relative frequences to the theoretical probabilities

N=1000; 
Geometrical=zeros(1,N);

for j=1:N
  
  Geo=0;    %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
  c=rand;    % This is the code written above for a single value of Geo
  X = (c<=p); 
  while X != 1  
    c=rand;     
    X = (c<=p);
    Geo++;        
  end       %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    
Geometrical(j)=Geo;
end

k=20;  % since we cannot plot the entire set of possible outcomes  
       % which contains an infinity number of elements we consider 
       % just the first 21 integers

Frq_abs=hist(Geometrical,0:k+1);%count the values 0 1 2 ... 20  and greater
                                % than 20 in vector
Frq_rel=Frq_abs(1:k+1)/N; %compute the relative frequencies of 0 ... 20
%{
disp('Estimated Geometrical distribution:')
disp([0: k; Frq_rel])
disp('Theoretical Geometrical distribution:')
disp([0:k; geopdf(0:k,p)])
%}
bar(0:k,Frq_rel,'b')
hold on
bar(0:k,geopdf(0:k,p),'y')
legend('Estimated','Theoretical')
set(findobj('type','patch'),'facealpha',0.7);
