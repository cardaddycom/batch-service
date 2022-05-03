package com.cardaddy.batch.job.tasklet;

import com.cardaddy.batch.domain.listing.VehicleListing;
import com.cardaddy.batch.repository.VehicleListingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class DeleteVehicleTasklet implements Tasklet, InitializingBean {

    @Autowired
    private VehicleListingRepository vehicleListingRepository;

    @Value("#{jobParameters['importTaskId']}")
    private Long importTaskId;

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        log.info("DeleteVehicleTasklet");
        //We need to delete vehicles this way inorder to keep the opensearch indexes in sync.
        List<Long> vehiclePks = vehicleListingRepository.vehiclesToBeDeleted(importTaskId, stepContribution.getStepExecution().getJobExecution().getJobId());

        Map<Boolean, List<Long>> groups = vehiclePks.stream().collect(Collectors.partitioningBy(s -> s > 30));
        List<List<Long>> subSets = new ArrayList<>(groups.values());

        for(List<Long> pkSet : subSets) {
            List<VehicleListing> vehicleListings = vehicleListingRepository.getVehicleListingByIdIn(pkSet);
            vehicleListingRepository.deleteAll(vehicleListings);
        }

        return RepeatStatus.FINISHED;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

}
