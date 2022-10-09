package jp.btsol.mahjong.application.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;

import jp.btsol.mahjong.application.repository.BaseRepository;
import jp.btsol.mahjong.entity.Notice;
import jp.btsol.mahjong.model.Message;

/**
 * MahjongService
 * 
 * @author B&T Solutions Inc.
 *
 */
@Service
@EnableConfigurationProperties(ServiceProperties.class)
@Transactional
public class MahjongNoticeService {
    /**
     * baseRepository
     */
    private final BaseRepository baseRepository;

    /**
     * ServiceProperties
     */
    private final ServiceProperties serviceProperties;

    /**
     * Constructor
     * 
     * @param serviceProperties ServiceProperties
     * @param baseRepository    BaseRepository
     */
    public MahjongNoticeService(ServiceProperties serviceProperties, BaseRepository baseRepository) {
        this.serviceProperties = serviceProperties;
        this.baseRepository = baseRepository;
    }

    /**
     * get message
     * 
     * @return List<Message>
     */
    public List<Message> messages() {
        MapSqlParameterSource param = new MapSqlParameterSource();
        TimeZone timeZone = TimeZone.getTimeZone("Asia/Tokyo");
        Date sysDate = new Date();
        LocalDate localSysDate = sysDate.toInstant().atZone(timeZone.toZoneId()).toLocalDate();
        param.addValue("today", localSysDate);
        List<Notice> notices = baseRepository.findForList(
                "select * from notice where notice.start_date <= :today and (notice.end_date is null or notice.end_date >= :today) order by notice.start_date ",
                param, Notice.class);
        return notices.stream().map(notice -> new Message(notice.getTitle(), notice.getDetail(), notice.getStartDate(),
                notice.getEndDate())).collect(Collectors.toList());
    }
}