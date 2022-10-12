package ru.drugsdomain.TimeScheduler.jobs;

import org.springframework.scheduling.annotation.Scheduled;
import ru.drugsdomain.TimeScheduler.util.LogExecutionTime;

public class ParserLaunchJob implements ScheduledJob {

    // at 03:00 am
    @Override
    @LogExecutionTime
    @Scheduled(cron = "0 0 3 ? * *")
    public void executeJob() {
        // сигнал master-парсеру, что можно начинать
    }
}
