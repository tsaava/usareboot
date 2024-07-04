package com.usareboot.back.services;

import com.usareboot.back.models.AlbumRowRequestDTO;
import com.usareboot.back.models.AlbumsDTO;
import com.usareboot.back.models.CardsDTO;
import com.usareboot.back.entities.AlbumsEntity;
import com.usareboot.back.entities.DStatusesEntity;
import com.usareboot.back.repositories.AlbumsItemsRepository;
import com.usareboot.back.repositories.AlbumsRepository;
import com.usareboot.back.repositories.CardsRepository;
import com.usareboot.back.repositories.DStatusRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.ArrayList;

@Service
public class AlbumsDAO {
    @Autowired
    private Environment environment;
    @Autowired
    private  AlbumsRepository albumsRepository;
    @Autowired
    private CardsRepository cardsRepository;

    @Autowired
    private DStatusRepository statusRepository;

    @Autowired
    private AlbumsItemsRepository albumsItemsRepository;

    @Autowired
    ModelMapper modelMapper;

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    public AlbumsDAO(AlbumsRepository albumsRepository,CardsRepository cardsRepository){
        this.albumsRepository=albumsRepository;
        this.cardsRepository=cardsRepository;
    }

    /**
     * Список альбомов
     * @return
     */
    public ArrayList<AlbumsDTO> getListAlbums() {
        ArrayList<AlbumsDTO> albumsDTOArrayList = new ArrayList<>();
        long millis=System.currentTimeMillis();
//        java.sql.Date date = new java.sql.Date(millis);
        java.sql.Date sqlDate = new java.sql.Date(millis);
        var datePolGoda=new java.sql.Date(sqlDate.getTime() - (190L *24*60*60*1000));
        System.out.println(datePolGoda);
        var bdFuncResponse = albumsRepository.getAlbumsEntitiesByAlbumDateAfterOrderByAlbumDate(datePolGoda);
        if (!bdFuncResponse.isEmpty()) {
            bdFuncResponse.forEach(x -> albumsDTOArrayList.add(new AlbumsDTO(
                    x.getAlbumId(),
                    x.getAlbumName(),
                    x.getAlbumDate().toString(),
                    x.getAlbumDatePlane() != null ?x.getAlbumDatePlane().toString():null,
                    x.getCountOrder(),
                    x.getCountry(),
                    x.getAlbumDesc(),
                    x.getShopUrl(),
                    x.getAlbumVkUrl(),
                    x.getPackageId(),
                    x.getCourseBank(),
                    x.getCourseAlbum(),
                    x.getBankName(),
                    x.getTrackNumber(),
                    x.getWarehouse(),
                    x.getAlbumDateStop()!= null ? x.getAlbumDateStop().toString():null,
                    x.getAlbumVkId(),
//                    x.getStatusId(),
                    x.getCourseBankId(),
//                    x.getCardId(),
                    x.getStatuses()!= null ? x.getStatuses().getStatusId(): null,
                    x.getStatuses()!= null ? x.getStatuses().getStatusName():null,
                    x.getCards()!= null ? x.getCards().getCardId():null,
                    x.getCards()!= null ? x.getCards().getCardName():null
            )));

        }
//        System.out.println(albumsDTOArrayList);
        return albumsDTOArrayList;
    }

    @Transactional
    public void albumsAdd( AlbumsEntity albumsEntity, Integer id) {
        DStatusesEntity dst=new DStatusesEntity();
        dst.setStatusId(17);
        String groupId=environment.getRequiredProperty("vk.groupId");
        albumsEntity.setAlbumVkUrl("https://vk.com/album-"+groupId+"_"+id);
        albumsEntity.setStatuses(dst);
        System.out.println("https://vk.com/album-"+groupId+"_"+id);
        albumsEntity.setAlbumVkId(id);
        this.entityManager.persist(albumsEntity);
    }

    @Transactional
    public void albumsUpd( AlbumRowRequestDTO albumRowRequestDTO, long id) {
//        AlbumsEntity album = modelMapper.map(albumRowRequestDTO, AlbumsEntity.class);
        AlbumsEntity temp = albumsRepository.findAlbumsEntityByAlbumId(albumRowRequestDTO.getAlbumId());
        temp.setAlbumDesc(albumRowRequestDTO.getAlbumDesc());
        temp.setCourseAlbum(albumRowRequestDTO.getCourseAlbum());
        temp.setAlbumDatePlane(albumRowRequestDTO.getAlbumDatePlane());
        temp.setCountOrder(albumRowRequestDTO.getCountOrder());
        temp.setAlbumDateStop(albumRowRequestDTO.getAlbumDateStop());
        if(albumRowRequestDTO.getAlbumStatus()!=null && !(albumRowRequestDTO.getAlbumStatus().isEmpty()))
            temp.setStatuses(statusRepository.findDStatusesEntityByStatusName( albumRowRequestDTO.getAlbumStatus()));
        if(albumRowRequestDTO.getCard()!=null && !(albumRowRequestDTO.getCard().isEmpty()))
            temp.setCards(cardsRepository.findCardsEntityByCardName( albumRowRequestDTO.getCard()));

        albumsRepository.save(temp);
//        DStatusesEntity dst=new DStatusesEntity();
//        dst.setStatusId(17);
//        this.entityManager.persist(albumsEntity);
    }

    /**
     * Список наименования карт для оплаты выкупов
     * @return
     */
    public ArrayList<CardsDTO> getAlbumCards() {
        ArrayList<CardsDTO> list = new ArrayList<>();
        var bdFuncResponse = cardsRepository.findAll();
        if (!bdFuncResponse.isEmpty()) {
            bdFuncResponse.forEach(x -> list.add(new CardsDTO(
                    x.getCardId(),
                    x.getCardName(),
                    x.getPercent()
            )));
        }
//        System.out.println(list);
        return list;
    }

    public void convertToEntity(AlbumRowRequestDTO albumRowRequestDTO) throws ParseException {
       /* AlbumsEntity album = modelMapper.map(albumRowRequestDTO, AlbumsEntity.class);
        AlbumsEntity temp = albumsRepository.findAlbumsEntityByAlbumId(albumRowRequestDTO.getAlbumId());
        temp.setAlbumDesc(albumRowRequestDTO.getAlbumDesc());
        temp.setCourseAlbum(albumRowRequestDTO.getCourseAlbum());
        temp.setAlbumDatePlane(albumRowRequestDTO.getAlbumDatePlane());
        temp.setCountOrder(albumRowRequestDTO.getCountOrder());
        temp.setAlbumDateStop(albumRowRequestDTO.getAlbumDateStop());
        albumsRepository.save(temp);*/
//        temp.getCourseAlbum(albumRowRequestDTO.getCourseAlbum());
//        post.setSubmissionDate(albumRowRequestDTO.getSubmissionDateConverted(
//                userService.getCurrentUser().getPreference().getTimezone()));

//        if (albumRowRequestDTO.getAlbumId() != null) {
//            AlbumsEntity oldPost = postService.getPostById(albumRowRequestDTO.getId());
//            post.setRedditID(oldPost.getRedditID());
//            post.setSent(oldPost.isSent());
//        }
//        return album;
    }


}
