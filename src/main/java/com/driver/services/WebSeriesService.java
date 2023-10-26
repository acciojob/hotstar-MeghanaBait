package com.driver.services;

import com.driver.EntryDto.WebSeriesEntryDto;
import com.driver.model.ProductionHouse;
import com.driver.model.WebSeries;
import com.driver.repository.ProductionHouseRepository;
import com.driver.repository.WebSeriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WebSeriesService {

    @Autowired
    WebSeriesRepository webSeriesRepository;

    @Autowired
    ProductionHouseRepository productionHouseRepository;

    public Integer addWebSeries(WebSeriesEntryDto webSeriesEntryDto)throws  Exception{

        //Add a webSeries to the database and update the ratings of the productionHouse
        //Incase the seriesName is already present in the Db throw Exception("Series is already present")
        //use function written in Repository Layer for the same
        //Dont forget to save the production and webseries Repo

        WebSeries webSeries= webSeriesRepository.findBySeriesName(webSeriesEntryDto.getSeriesName());
        if(webSeries != null){
            throw new Exception("Series is already present");
        }


        ProductionHouse productionHouse = null;
        Optional<ProductionHouse> optionalProductionHouse = productionHouseRepository.findById(webSeriesEntryDto.getProductionHouseId());
        if(optionalProductionHouse.isPresent()){
            productionHouse = optionalProductionHouse.get();
        }

        WebSeries webSeries1 = new WebSeries();
        webSeries1.setSeriesName(webSeriesEntryDto.getSeriesName());
        webSeries1.setAgeLimit(webSeriesEntryDto.getAgeLimit());
        webSeries1.setRating(webSeriesEntryDto.getRating());;
        webSeries1.setSubscriptionType(webSeriesEntryDto.getSubscriptionType());

        productionHouse.getWebSeriesList().add(webSeries1);

        //calculating production house rating
        List<WebSeries> webSeriesList = productionHouse.getWebSeriesList();
        double rating = 0;
        for(WebSeries webSeries2 : webSeriesList){
            rating += webSeries2.getRating();
        }

        rating = rating /webSeriesList.size();

        productionHouse.setRatings(rating);

        //saving

        productionHouseRepository.save(productionHouse);

        return webSeries1.getId();
    }

}
