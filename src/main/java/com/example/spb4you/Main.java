package com.example.spb4you;

import com.example.spb4you.models.Category;
import com.example.spb4you.models.Location;
import com.example.spb4you.models.Tag;
import com.example.spb4you.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Main implements CommandLineRunner {

    @Autowired
    private LocationService locationService;

    @Autowired
    private RouteService routeService;

    @Autowired
    private TagService tagService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private PhotoService photoService;

    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) throws Exception {

        System.out.println("Приложение запустилось");

        // Ваш код для создания и сохранения локации
        Location location = new Location();
        //location.setId(1);
        location.setName("Толстовский дом");
        location.setDescription("Доходный дом графа М. П. Толстого — историческое здание в Центральном районе Санкт-Петербурга.  \n" +
                "\n" +
                "Здание оформлено в стиле северного модерна и отличается оригинальным планировочным решением — из-за неправильной формы земельного участка архитектор Лидваль спроектировал три переходящих друг в друга двора, образующих внутреннюю улицу,  которую неофициально называют улицей зодчего Лидваля. Из-за неправильной конфигурации участка продольная ось дворов имеет излом, поэтому аркады не образуют сквозной перспективы: они раскрываются поочерёдно, что придаёт композиции особенные эффектность и выразительность. \n" +
                "Наиболее выразительным архитектурным украшением дома являются трёхпролётные арки, ведущие во дворы и соединяющие их.\n" +
                "\n" +
                "Доходный дом Толстого задумывался как жильё для всех сословий, квартиры различались по площади, чтобы соответствовать возможностям людей разного достатка. Для всех жильцов были доступны самые современные, на тот момент, удобства. \n" +
                "\n" +
                "После 1918 года дом национализировали, жильё стали официально «уплотнять» и превращать в коммунальное, под квартиры отдали даже гаражи и подсобки. Некоторые квартиры, напротив, переформатировали в нежилые помещения: например, в квартире № 108 открылась театральная студия Николая Смолича.\n" +
                "\n" +
                "После войны от концепции проездных дворов Лидваля отошли: в них постелили газоны и высадили тополя, установили фонтан с бетонным вазоном посередине. Во внутреннем дворе снимались «лондонские» сцены советского телесериала «Шерлок Холмс» с Василием Ливановым в главной роли. Толстовский дом «участвовал» в съёмках фильмов «Зимняя вишня», «Рожденная революцией», «Вам и не снилось», «Бандитский Петербург» и другие.\n" +
                "\n" +
                "За более чем сто лет с момента постройки жильцами дома являлись многие выдающиеся деятели культуры, науки, политики.\n" +
                "Дом имеет статус объекта культурного наследия регионального значения.");
        location.setGeoData("59.929111, 30.343720");
        location.setAdditInfo("Адрес: ул. Рубинштейна, 15-17\n");
        location.setLikes(0);
        //location.setCategoryId(1);
        //locationService.save(location);
        //System.out.println(locationService.findById(1).toString());

        Tag tag1 = new Tag();
        tag1.setName("Бары");
        tag1.setColor("blue");
        //tagService.save(tag1);

        Category category = new Category();
        category.setName("Все локации");
        category.setDescription("На данной карте размещены все локации из всех катерогий.\n" +
                "\n" +
                "Нажимая на локацию на карте вы сможете ознакомиться с данным местом более подробно.");
        //categoryService.save(category);
    }
}

