package com.driver.services;


import com.driver.EntryDto.SubscriptionEntryDto;
import com.driver.model.Subscription;
import com.driver.model.SubscriptionType;
import com.driver.model.User;
import com.driver.repository.SubscriptionRepository;
import com.driver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SubscriptionService {

    @Autowired
    SubscriptionRepository subscriptionRepository;

    @Autowired
    UserRepository userRepository;

    public Integer buySubscription(SubscriptionEntryDto subscriptionEntryDto){

        //Save The subscription Object into the Db and return the total Amount that user has to pay

        //calculating total amount
        Integer noOfScreensRequired = subscriptionEntryDto.getNoOfScreensRequired();
        Integer totalAmount = 0;

        if(subscriptionEntryDto.getSubscriptionType().toString().equals("BASIC")){
            totalAmount = 500 + (200 * noOfScreensRequired);
        }else if (subscriptionEntryDto.getSubscriptionType().toString().equals("PRO")){
            totalAmount = 800 + (250 * noOfScreensRequired);
        }else if (subscriptionEntryDto.getSubscriptionType().toString().equals("ELITE")){
            totalAmount = 1000 + (350 * noOfScreensRequired);
        }

        //get user info
        User user = userRepository.findById(subscriptionEntryDto.getUserId()).get();

        //Setting subscription parameters
        Subscription subscription = new Subscription();
        subscription.setSubscriptionType(subscriptionEntryDto.getSubscriptionType());
        subscription.setNoOfScreensSubscribed(subscriptionEntryDto.getNoOfScreensRequired());
        subscription.setUser(user);
        subscription.setTotalAmountPaid(totalAmount);

        //setting User FK
        user.setSubscription(subscription);

        return totalAmount;



    }

    public Integer upgradeSubscription(Integer userId)throws Exception{

        //If you are already at an ElITE subscription : then throw Exception ("Already the best Subscription")
        //In all other cases just try to upgrade the subscription and tell the difference of price that user has to pay
        //update the subscription in the repository

        User user = userRepository.findById(userId).get();
        if(user.getSubscription().getSubscriptionType().toString().equals("ELITE")){
            throw new Exception("Already the best Subscription");
        }

        Subscription subscription = user.getSubscription();
        Integer previousFair = subscription.getTotalAmountPaid();
        Integer currFair;

        if(subscription.getSubscriptionType().equals(SubscriptionType.BASIC)){
            subscription.setSubscriptionType(SubscriptionType.PRO);
            currFair = previousFair+300+(50*subscription.getNoOfScreensSubscribed());
        }else{
            subscription.setSubscriptionType(SubscriptionType.ELITE);
            currFair =previousFair+200+(100*subscription.getNoOfScreensSubscribed());
        }

        subscription.setTotalAmountPaid(currFair);
        user.setSubscription(subscription);
        subscriptionRepository.save(subscription);

        return currFair-previousFair;
    }

    public Integer calculateTotalRevenueOfHotstar(){

        //We need to find out total Revenue of hotstar : from all the subscriptions combined
        //Hint is to use findAll function from the SubscriptionDb

        List<Subscription> subscriptionList = subscriptionRepository.findAll();
        Integer totalRevenue = 0;

        for(Subscription subscription:subscriptionList){
            totalRevenue += subscription.getTotalAmountPaid();
        }
        return totalRevenue;
    }

}
