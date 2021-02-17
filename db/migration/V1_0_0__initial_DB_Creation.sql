--
-- PostgreSQL database dump
--

-- Dumped from database version 10.13
-- Dumped by pg_dump version 10.13

-- Started on 2021-01-31 21:31:47

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 11 (class 2615 OID 16421)
-- Name: movie; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA IF NOT EXISTS movie;


--ALTER SCHEMA movie OWNER TO postgres;

--
-- TOC entry 2933 (class 0 OID 0)
-- Dependencies: 11
-- Name: SCHEMA movie; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON SCHEMA movie IS 'movieLand DB';


--
-- TOC entry 213 (class 1259 OID 16537)
-- Name: country_cntr_id_seq; Type: SEQUENCE; Schema: movie; Owner: postgres
--

CREATE SEQUENCE movie.country_cntr_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--ALTER TABLE movie.country_cntr_id_seq OWNER TO postgres;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 204 (class 1259 OID 16430)
-- Name: country; Type: TABLE; Schema: movie; Owner: postgres
--

CREATE TABLE movie.country (
    cntr_id numeric DEFAULT nextval('movie.country_cntr_id_seq'::regclass) NOT NULL,
    cntr_name character varying(64) NOT NULL
);


--ALTER TABLE movie.country OWNER TO postgres;

--
-- TOC entry 211 (class 1259 OID 16530)
-- Name: genre_gnr_id_seq; Type: SEQUENCE; Schema: movie; Owner: postgres
--

CREATE SEQUENCE movie.genre_gnr_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--ALTER TABLE movie.genre_gnr_id_seq OWNER TO postgres;

--
-- TOC entry 205 (class 1259 OID 16443)
-- Name: genre; Type: TABLE; Schema: movie; Owner: postgres
--

CREATE TABLE movie.genre (
    gnr_id numeric DEFAULT nextval('movie.genre_gnr_id_seq'::regclass) NOT NULL,
    gnr_name character varying(64) NOT NULL
);


--ALTER TABLE movie.genre OWNER TO postgres;

--
-- TOC entry 212 (class 1259 OID 16535)
-- Name: movie_m_id_seq; Type: SEQUENCE; Schema: movie; Owner: postgres
--

CREATE SEQUENCE movie.movie_m_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--ALTER TABLE movie.movie_m_id_seq OWNER TO postgres;

--
-- TOC entry 203 (class 1259 OID 16422)
-- Name: movie; Type: TABLE; Schema: movie; Owner: postgres
--

CREATE TABLE movie.movie (
    m_id numeric DEFAULT nextval('movie.movie_m_id_seq'::regclass) NOT NULL,
    m_title character varying(64) NOT NULL,
    m_title_en character varying(64),
    m_price double precision NOT NULL,
    m_release_year character(4),
    m_description text,
    m_rating double precision
);


--ALTER TABLE movie.movie OWNER TO postgres;

--
-- TOC entry 207 (class 1259 OID 16469)
-- Name: movie_country; Type: TABLE; Schema: movie; Owner: postgres
--

CREATE TABLE movie.movie_country (
    m_id numeric DEFAULT nextval('movie.country_cntr_id_seq'::regclass) NOT NULL,
    cntr_id numeric NOT NULL
);


--ALTER TABLE movie.movie_country OWNER TO postgres;

--
-- TOC entry 206 (class 1259 OID 16451)
-- Name: movie_genre; Type: TABLE; Schema: movie; Owner: postgres
--

CREATE TABLE movie.movie_genre (
    gnr_id numeric NOT NULL,
    m_id numeric NOT NULL
);


--ALTER TABLE movie.movie_genre OWNER TO postgres;

--
-- TOC entry 214 (class 1259 OID 16539)
-- Name: movie_poster_poster_id_seq; Type: SEQUENCE; Schema: movie; Owner: postgres
--

CREATE SEQUENCE movie.movie_poster_poster_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--ALTER TABLE movie.movie_poster_poster_id_seq OWNER TO postgres;

--
-- TOC entry 210 (class 1259 OID 16517)
-- Name: movie_poster; Type: TABLE; Schema: movie; Owner: postgres
--

CREATE TABLE movie.movie_poster (
    poster_id numeric DEFAULT nextval('movie.movie_poster_poster_id_seq'::regclass) NOT NULL,
    picture_url character varying(256) NOT NULL,
    m_id numeric
);


--ALTER TABLE movie.movie_poster OWNER TO postgres;

--
-- TOC entry 215 (class 1259 OID 16542)
-- Name: movie_review_review_id_seq; Type: SEQUENCE; Schema: movie; Owner: postgres
--

CREATE SEQUENCE movie.movie_review_review_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--ALTER TABLE movie.movie_review_review_id_seq OWNER TO postgres;

--
-- TOC entry 209 (class 1259 OID 16498)
-- Name: movie_review; Type: TABLE; Schema: movie; Owner: postgres
--

CREATE TABLE movie.movie_review (
    review_id numeric DEFAULT nextval('movie.movie_review_review_id_seq'::regclass) NOT NULL,
    usr_id numeric NOT NULL,
    message text NOT NULL,
    m_id numeric NOT NULL,
    review_date date DEFAULT CURRENT_DATE NOT NULL
);


--ALTER TABLE movie.movie_review OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 16544)
-- Name: user_usr_id_seq; Type: SEQUENCE; Schema: movie; Owner: postgres
--

CREATE SEQUENCE movie.user_usr_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--ALTER TABLE movie.user_usr_id_seq OWNER TO postgres;

--
-- TOC entry 208 (class 1259 OID 16487)
-- Name: user; Type: TABLE; Schema: movie; Owner: postgres
--

CREATE TABLE movie."user" (
    usr_id numeric DEFAULT nextval('movie.user_usr_id_seq'::regclass) NOT NULL,
    usr_name character varying(32) NOT NULL,
    usr_email character varying(32),
    usr_password character varying(128) NOT NULL,
    usr_sole character varying(32),
    usr_password_enc character varying(128)
);


--ALTER TABLE movie."user" OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 16578)
-- Name: v_all_movie_countries_ui; Type: VIEW; Schema: movie; Owner: postgres
--

CREATE VIEW movie.v_all_movie_countries_ui AS
 SELECT cntr.cntr_id,
    cntr.cntr_name,
    mc.m_id
   FROM (movie.country cntr
     JOIN movie.movie_country mc ON ((cntr.cntr_id = mc.cntr_id)));


--ALTER TABLE movie.v_all_movie_countries_ui OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 16574)
-- Name: v_all_movie_genres_ui; Type: VIEW; Schema: movie; Owner: postgres
--

CREATE VIEW movie.v_all_movie_genres_ui AS
 SELECT g.gnr_id,
    g.gnr_name,
    mg.m_id
   FROM (movie.genre g
     JOIN movie.movie_genre mg ON ((g.gnr_id = mg.gnr_id)));


--ALTER TABLE movie.v_all_movie_genres_ui OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 16560)
-- Name: v_movie_ui; Type: VIEW; Schema: movie; Owner: postgres
--

CREATE VIEW movie.v_movie_ui AS
 SELECT m.m_id,
    m.m_title,
    m.m_title_en,
    m.m_price,
    m.m_release_year,
    m.m_description,
    m.m_rating,
    mpos.picture_url
   FROM (movie.movie m
     JOIN movie.movie_poster mpos ON ((mpos.m_id = m.m_id)));


--ALTER TABLE movie.v_movie_ui OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 16570)
-- Name: v_movie_genre_ui; Type: VIEW; Schema: movie; Owner: postgres
--

CREATE VIEW movie.v_movie_genre_ui AS
 SELECT m.m_id,
    m.m_title,
    m.m_title_en,
    m.m_price,
    m.m_release_year,
    m.m_description,
    m.m_rating,
    m.picture_url,
    g.gnr_id
   FROM (movie.v_movie_ui m
     JOIN movie.movie_genre g ON ((m.m_id = g.m_id)));


--ALTER TABLE movie.v_movie_genre_ui OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 24785)
-- Name: v_movie_ui_v2; Type: VIEW; Schema: movie; Owner: postgres
--

CREATE VIEW movie.v_movie_ui_v2 AS
 SELECT m.m_id,
    m.m_title AS m_russian_name,
    m.m_title_en AS m_native_name,
    m.m_price,
    m.m_release_year,
    m.m_description,
    m.m_rating,
    mpos.picture_url
   FROM (movie.movie m
     JOIN movie.movie_poster mpos ON ((mpos.m_id = m.m_id)));


--ALTER TABLE movie.v_movie_ui_v2 OWNER TO postgres;

-- TOC entry 2915 (class 0 OID 16430)
-- Dependencies: 204
-- Data for Name: country; Type: TABLE DATA; Schema: movie; Owner: postgres
--

INSERT INTO movie.country VALUES (37, 'США');
INSERT INTO movie.country VALUES (38, 'Франция');
INSERT INTO movie.country VALUES (39, ' Великобритания');
INSERT INTO movie.country VALUES (40, 'Италия');
INSERT INTO movie.country VALUES (41, ' Германия');
INSERT INTO movie.country VALUES (42, 'Япония');
INSERT INTO movie.country VALUES (43, 'Великобритания');
INSERT INTO movie.country VALUES (44, ' США');
INSERT INTO movie.country VALUES (45, ' Испания');


--
-- TOC entry 2916 (class 0 OID 16443)
-- Dependencies: 205
-- Data for Name: genre; Type: TABLE DATA; Schema: movie; Owner: postgres
--

INSERT INTO movie.genre VALUES (63, 'драма');
INSERT INTO movie.genre VALUES (64, 'криминал');
INSERT INTO movie.genre VALUES (65, 'фэнтези');
INSERT INTO movie.genre VALUES (66, 'детектив');
INSERT INTO movie.genre VALUES (67, 'мелодрама');
INSERT INTO movie.genre VALUES (68, 'биография');
INSERT INTO movie.genre VALUES (69, 'комедия');
INSERT INTO movie.genre VALUES (70, 'фантастика');
INSERT INTO movie.genre VALUES (71, 'боевик');
INSERT INTO movie.genre VALUES (72, 'триллер');
INSERT INTO movie.genre VALUES (73, 'приключения');
INSERT INTO movie.genre VALUES (74, 'аниме');
INSERT INTO movie.genre VALUES (75, 'мультфильм');
INSERT INTO movie.genre VALUES (76, 'семейный');
INSERT INTO movie.genre VALUES (77, 'вестерн');


--
-- TOC entry 2914 (class 0 OID 16422)
-- Dependencies: 203
-- Data for Name: movie; Type: TABLE DATA; Schema: movie; Owner: postgres
--

INSERT INTO movie.movie VALUES (102, 'Побег из Шоушенка', 'The Shawshank Redemption', 123.45, '1994', 'Успешный банкир Энди Дюфрейн обвинен в убийстве собственной жены и ее любовника. Оказавшись в тюрьме под названием Шоушенк, он сталкивается с жестокостью и беззаконием, царящими по обе стороны решетки. Каждый, кто попадает в эти стены, становится их рабом до конца жизни. Но Энди, вооруженный живым умом и доброй душой, отказывается мириться с приговором судьбы и начинает разрабатывать невероятно дерзкий план своего освобождения.', 8.9000000000000004);
INSERT INTO movie.movie VALUES (103, 'Зеленая миля', 'The Green Mile', 134.66999999999999, '1999', 'Обвиненный в страшном преступлении, Джон Коффи оказывается в блоке смертников тюрьмы «Холодная гора». Вновь прибывший обладал поразительным ростом и был пугающе спокоен, что, впрочем, никак не влияло на отношение к нему начальника блока Пола Эджкомба, привыкшего исполнять приговор.', 8.9000000000000004);
INSERT INTO movie.movie VALUES (104, 'Форрест Гамп', 'Forrest Gump', 200.59999999999999, '1994', 'От лица главного героя Форреста Гампа, слабоумного безобидного человека с благородным и открытым сердцем, рассказывается история его необыкновенной жизни.Фантастическим образом превращается он в известного футболиста, героя войны, преуспевающего бизнесмена. Он становится миллиардером, но остается таким же бесхитростным, глупым и добрым. Форреста ждет постоянный успех во всем, а он любит девочку, с которой дружил в детстве, но взаимность приходит слишком поздно.', 8.5999999999999996);
INSERT INTO movie.movie VALUES (105, 'Список Шиндлера', 'Schindler''s List', 150.5, '1993', 'Фильм рассказывает реальную историю загадочного Оскара Шиндлера, члена нацистской партии, преуспевающего фабриканта, спасшего во время Второй мировой войны почти 1200 евреев.', 8.6999999999999993);
INSERT INTO movie.movie VALUES (106, '1+1', 'Intouchables', 120, '2011', 'Пострадав в результате несчастного случая, богатый аристократ Филипп нанимает в помощники человека, который менее всего подходит для этой работы, — молодого жителя предместья Дрисса, только что освободившегося из тюрьмы. Несмотря на то, что Филипп прикован к инвалидному креслу, Дриссу удается привнести в размеренную жизнь аристократа дух приключений.', 8.3000000000000007);
INSERT INTO movie.movie VALUES (107, 'Начало', 'Inception', 130, '2010', 'Кобб — талантливый вор, лучший из лучших в опасном искусстве извлечения: он крадет ценные секреты из глубин подсознания во время сна, когда человеческий разум наиболее уязвим. Редкие способности Кобба сделали его ценным игроком в привычном к предательству мире промышленного шпионажа, но они же превратили его в извечного беглеца и лишили всего, что он когда-либо любил.', 8.5999999999999996);
INSERT INTO movie.movie VALUES (108, 'Жизнь прекрасна', 'La vita è bella', 145.99000000000001, '1997', 'Во время II Мировой войны в Италии в концлагерь были отправлены евреи, отец и его маленький сын. Жена, итальянка, добровольно последовала вслед за ними. В лагере отец сказал сыну, что все происходящее вокруг является очень большой игрой за приз в настоящий танк, который достанется тому мальчику, который сможет не попасться на глаза надзирателям. Он сделал все, чтобы сын поверил в игру и остался жив, прячась в бараке.', 8.1999999999999993);
INSERT INTO movie.movie VALUES (109, 'Бойцовский клуб', 'Fight Club', 119.98999999999999, '1999', 'Терзаемый хронической бессонницей и отчаянно пытающийся вырваться из мучительно скучной жизни, клерк встречает некоего Тайлера Дардена, харизматического торговца мылом с извращенной философией. Тайлер уверен, что самосовершенствование — удел слабых, а саморазрушение — единственное, ради чего стоит жить.', 8.4000000000000004);
INSERT INTO movie.movie VALUES (110, 'Звёздные войны: Эпизод 4 – Новая надежда', 'Star Wars', 198.97999999999999, '1977', 'Татуин. Планета-пустыня. Уже постаревший рыцарь Джедай Оби Ван Кеноби спасает молодого Люка Скайуокера, когда тот пытается отыскать пропавшего дроида. С этого момента Люк осознает свое истинное назначение: он один из рыцарей Джедай. В то время как гражданская война охватила галактику, а войска повстанцев ведут бои против сил злого Императора, к Люку и Оби Вану присоединяется отчаянный пилот-наемник Хан Соло, и в сопровождении двух дроидов, R2D2 и C-3PO, этот необычный отряд отправляется на поиски предводителя повстанцев — принцессы Леи. Героям предстоит отчаянная схватка с устрашающим Дартом Вейдером — правой рукой Императора и его секретным оружием — «Звездой Смерти».', 8.0999999999999996);
INSERT INTO movie.movie VALUES (111, 'Звёздные войны: Эпизод 5 – Империя наносит ответный удар', 'Star Wars: Episode V - The Empire Strikes Back', 198.97999999999999, '1980', 'Борьба за Галактику обостряется в пятом эпизоде космической саги. Войска Императора начинают массированную атаку на повстанцев и их союзников. Хан Соло и принцесса Лейя укрываются в Заоблачном Городе, в котором их и захватывает Дарт Вейдер, в то время как Люк Скайуокер находится на таинственной планете джунглей Дагоба. Там Мастер — джедай Йода обучает молодого рыцаря навыкам обретения Силы. Люк даже не предполагает, как скоро ему придется воспользоваться знаниями старого Мастера: впереди битва с превосходящими силами Императора и смертельный поединок с Дартом Вейдером.', 8.1999999999999993);
INSERT INTO movie.movie VALUES (112, 'Унесённые призраками', 'Sen to Chihiro no kamikakushi', 145.90000000000001, '2001', 'Маленькая Тихиро вместе с мамой и папой переезжают в новый дом. Заблудившись по дороге, они оказываются в странном пустынном городе, где их ждет великолепный пир. Родители с жадностью набрасываются на еду и к ужасу девочки превращаются в свиней, став пленниками злой колдуньи Юбабы, властительницы таинственного мира древних богов и могущественных духов.', 8.5999999999999996);
INSERT INTO movie.movie VALUES (113, 'Титаник', 'Titanic', 150, '1997', 'Молодые влюбленные Джек и Роза находят друг друга в первом и последнем плавании «непотопляемого» Титаника. Они не могли знать, что шикарный лайнер столкнется с айсбергом в холодных водах Северной Атлантики, и их страстная любовь превратится в схватку со смертью…', 7.9000000000000004);
INSERT INTO movie.movie VALUES (114, 'Пролетая над гнездом кукушки', 'One Flew Over the Cuckoo''s Nest', 180, '1975', 'Сымитировав помешательство в надежде избежать тюремного заключения, Рэндл Патрик МакМерфи попадает в психиатрическую клинику, где почти безраздельным хозяином является жестокосердная сестра Милдред Рэтчед. МакМерфи поражается тому, что прочие пациенты смирились с существующим положением вещей, а некоторые — даже сознательно пришли в лечебницу, прячась от пугающего внешнего мира. И решается на бунт. В одиночку.', 8.6999999999999993);
INSERT INTO movie.movie VALUES (115, 'Ходячий замок', 'Hauru no ugoku shiro', 130.5, '2004', 'Злая ведьма заточила 18-летнюю Софи в тело старухи. В поисках того, кто поможет ей вернуться к своему облику, Софи знакомится с могущественным волшебником Хаулом и его демоном Кальцифером. Кальцифер должен служить Хаулу по договору, условия которого он не может разглашать. Девушка и демон решают помочь друг другу избавиться от злых чар…', 8.1999999999999993);
INSERT INTO movie.movie VALUES (116, 'Гладиатор', 'Gladiator', 175, '2000', 'В великой Римской империи не было военачальника, равного генералу Максимусу. Непобедимые легионы, которыми командовал этот благородный воин, боготворили его и могли последовать за ним даже в ад. Но случилось так, что отважный Максимус, готовый сразиться с любым противником в честном бою, оказался бессилен против вероломных придворных интриг. Генерала предали и приговорили к смерти. Чудом избежав гибели, Максимус становится гладиатором.', 8.5999999999999996);
INSERT INTO movie.movie VALUES (117, 'Большой куш', 'Snatch.', 160, '2000', 'Четырехпалый Френки должен был переправить краденый алмаз из Англии в США своему боссу Эви. Но вместо этого герой попадает в эпицентр больших неприятностей. Сделав ставку на подпольном боксерском поединке, Френки попадает в круговорот весьма нежелательных событий. Вокруг героя и его груза разворачивается сложная интрига с участием множества колоритных персонажей лондонского дна — русского гангстера, троих незадачливых грабителей, хитрого боксера и угрюмого громилы грозного мафиози. Каждый норовит в одиночку сорвать Большой Куш.', 8.5);
INSERT INTO movie.movie VALUES (118, 'Темный рыцарь', 'The Dark Knight', 199.99000000000001, '2008', 'Бэтмен поднимает ставки в войне с криминалом. С помощью лейтенанта Джима Гордона и прокурора Харви Дента он намерен очистить улицы от преступности, отравляющей город. Сотрудничество оказывается эффективным, но скоро они обнаружат себя посреди хаоса, развязанного восходящим криминальным гением, известным испуганным горожанам под именем Джокер.', 8.5);
INSERT INTO movie.movie VALUES (119, 'Как приручить дракона', 'How to Train Your Dragon', 182, '2010', 'Вы узнаете историю подростка Иккинга, которому не слишком близки традиции его героического племени, много лет ведущего войну с драконами. Мир Иккинга переворачивается с ног на голову, когда он неожиданно встречает дракона Беззубика, который поможет ему и другим викингам увидеть привычный мир с совершенно другой стороны…', 8.1999999999999993);
INSERT INTO movie.movie VALUES (120, 'Молчание ягнят', 'The Silence of the Lambs', 150.5, '1990', 'Психопат похищает и убивает молодых женщин по всему Среднему Западу Америки. ФБР, уверенное в том, что все преступления совершены одним и тем же человеком, поручает агенту Клариссе Старлинг встретиться с заключенным-маньяком, который мог бы объяснить следствию психологические мотивы серийного убийцы и тем самым вывести на его след.', 8.3000000000000007);
INSERT INTO movie.movie VALUES (121, 'Гран Торино', 'Gran Torino', 100.5, '2008', 'Вышедший на пенсию автомеханик Уолт Ковальски проводит дни, починяя что-то по дому, попивая пиво и раз в месяц заходя к парикмахеру. И хотя последним желанием его недавно почившей жены было совершение им исповеди, Уолту — ожесточившемуся ветерану Корейской войны, всегда держащему свою винтовку наготове, — признаваться в общем-то не в чем. Да и нет того, кому он доверял бы в той полной мере, в какой доверяет своей собаке Дейзи.', 8.0999999999999996);
INSERT INTO movie.movie VALUES (122, 'Хороший, плохой, злой', 'Il buono, il brutto, il cattivo', 130, '1979', 'В разгар гражданской войны таинственный стрелок скитается по просторам Дикого Запада. У него нет ни дома, ни друзей, ни компаньонов, пока он не встречает двоих незнакомцев, таких же безжалостных и циничных. По воле судьбы трое мужчин вынуждены объединить свои усилия в поисках украденного золота. Но совместная работа — не самое подходящее занятие для таких отъявленных бандитов, как они. Компаньоны вскоре понимают, что в их дерзком и опасном путешествии по разоренной войной стране самое важное — никому не доверять и держать пистолет наготове, если хочешь остаться в живых.', 8.5);
INSERT INTO movie.movie VALUES (123, 'Укрощение строптивого', 'Il bisbetico domato', 120, '1980', 'Категорически не приемлющий женского общества грубоватый фермер вполне счастлив и доволен своей холостяцкой жизнью. Но неожиданно появившаяся в его жизни героиня пытается изменить его взгляды на жизнь и очаровать его. Что же из этого получится…', 7.7000000000000002);
INSERT INTO movie.movie VALUES (124, 'Блеф', 'Bluff storia di truffe e di imbroglioni', 100, '1976', 'Белль Дюк имеет старые счеты с Филиппом Бэнгом, который отбывает свой срок за решёткой. Для того чтобы поквитаться с Филиппом, Белль Дюк вступает в сговор с другим аферистом по имени Феликс, чтобы тот организовал побег Филиппа Бенга из тюрьмы. Побег удаётся, но парочка заодно обманывает и Белль Дюк, исчезнув прямо из-под её носа. Выясняется, что и Филипп Бэнг, в свою очередь, не прочь отомстить Белль Дюк. Для этого он задумывает грандиозную мистификацию, сродни покерному блефу…', 7.5999999999999996);
INSERT INTO movie.movie VALUES (125, 'Джанго освобожденный', 'Django Unchained', 170, '2012', 'Эксцентричный охотник за головами, также известный как «Дантист», промышляет отстрелом самых опасных преступников. Работенка пыльная, и без надежного помощника ему не обойтись. Но как найти такого и желательно не очень дорогого? Беглый раб по имени Джанго — прекрасная кандидатура. Правда, у нового помощника свои мотивы — кое с чем надо разобраться…', 8.5);
INSERT INTO movie.movie VALUES (126, 'Танцующий с волками', 'Dances with Wolves', 120.55, '1990', 'Действие фильма происходит в США во времена Гражданской войны. Лейтенант американской армии Джон Данбар после ранения в бою просит перевести его на новое место службы ближе к западной границе США. Место его службы отдалённый маленький форт. Непосредственный его командир покончил жизнь самоубийством, а попутчик Данбара погиб в стычке с индейцами после того, как довез героя до места назначения. Людей, знающих, что Данбар остался один в форте и должен выжить в условиях суровой природы, и в соседстве с кажущимися негостеприимными коренными обитателями Северной Америки, просто не осталось. Казалось, он покинут всеми. Постепенно лейтенант осваивается, он ведет записи в дневнике…', 8);


--
-- TOC entry 2918 (class 0 OID 16469)
-- Dependencies: 207
-- Data for Name: movie_country; Type: TABLE DATA; Schema: movie; Owner: postgres
--

INSERT INTO movie.movie_country VALUES (102, 37);
INSERT INTO movie.movie_country VALUES (103, 37);
INSERT INTO movie.movie_country VALUES (104, 37);
INSERT INTO movie.movie_country VALUES (105, 37);
INSERT INTO movie.movie_country VALUES (106, 38);
INSERT INTO movie.movie_country VALUES (107, 37);
INSERT INTO movie.movie_country VALUES (107, 39);
INSERT INTO movie.movie_country VALUES (108, 40);
INSERT INTO movie.movie_country VALUES (109, 37);
INSERT INTO movie.movie_country VALUES (109, 41);
INSERT INTO movie.movie_country VALUES (110, 37);
INSERT INTO movie.movie_country VALUES (111, 37);
INSERT INTO movie.movie_country VALUES (112, 42);
INSERT INTO movie.movie_country VALUES (113, 37);
INSERT INTO movie.movie_country VALUES (114, 37);
INSERT INTO movie.movie_country VALUES (115, 42);
INSERT INTO movie.movie_country VALUES (116, 37);
INSERT INTO movie.movie_country VALUES (116, 39);
INSERT INTO movie.movie_country VALUES (117, 43);
INSERT INTO movie.movie_country VALUES (117, 44);
INSERT INTO movie.movie_country VALUES (118, 37);
INSERT INTO movie.movie_country VALUES (118, 39);
INSERT INTO movie.movie_country VALUES (119, 37);
INSERT INTO movie.movie_country VALUES (120, 37);
INSERT INTO movie.movie_country VALUES (121, 37);
INSERT INTO movie.movie_country VALUES (121, 41);
INSERT INTO movie.movie_country VALUES (122, 40);
INSERT INTO movie.movie_country VALUES (122, 45);
INSERT INTO movie.movie_country VALUES (122, 41);
INSERT INTO movie.movie_country VALUES (122, 44);
INSERT INTO movie.movie_country VALUES (123, 40);
INSERT INTO movie.movie_country VALUES (124, 40);
INSERT INTO movie.movie_country VALUES (125, 37);
INSERT INTO movie.movie_country VALUES (126, 37);
INSERT INTO movie.movie_country VALUES (126, 39);


--
-- TOC entry 2917 (class 0 OID 16451)
-- Dependencies: 206
-- Data for Name: movie_genre; Type: TABLE DATA; Schema: movie; Owner: postgres
--

INSERT INTO movie.movie_genre VALUES (63, 102);
INSERT INTO movie.movie_genre VALUES (64, 102);
INSERT INTO movie.movie_genre VALUES (65, 103);
INSERT INTO movie.movie_genre VALUES (63, 103);
INSERT INTO movie.movie_genre VALUES (64, 103);
INSERT INTO movie.movie_genre VALUES (66, 103);
INSERT INTO movie.movie_genre VALUES (63, 104);
INSERT INTO movie.movie_genre VALUES (67, 104);
INSERT INTO movie.movie_genre VALUES (63, 105);
INSERT INTO movie.movie_genre VALUES (68, 105);
INSERT INTO movie.movie_genre VALUES (63, 106);
INSERT INTO movie.movie_genre VALUES (69, 106);
INSERT INTO movie.movie_genre VALUES (68, 106);
INSERT INTO movie.movie_genre VALUES (70, 107);
INSERT INTO movie.movie_genre VALUES (71, 107);
INSERT INTO movie.movie_genre VALUES (72, 107);
INSERT INTO movie.movie_genre VALUES (63, 107);
INSERT INTO movie.movie_genre VALUES (66, 107);
INSERT INTO movie.movie_genre VALUES (63, 108);
INSERT INTO movie.movie_genre VALUES (67, 108);
INSERT INTO movie.movie_genre VALUES (69, 108);
INSERT INTO movie.movie_genre VALUES (72, 109);
INSERT INTO movie.movie_genre VALUES (63, 109);
INSERT INTO movie.movie_genre VALUES (64, 109);
INSERT INTO movie.movie_genre VALUES (70, 110);
INSERT INTO movie.movie_genre VALUES (65, 110);
INSERT INTO movie.movie_genre VALUES (71, 110);
INSERT INTO movie.movie_genre VALUES (73, 110);
INSERT INTO movie.movie_genre VALUES (70, 111);
INSERT INTO movie.movie_genre VALUES (65, 111);
INSERT INTO movie.movie_genre VALUES (71, 111);
INSERT INTO movie.movie_genre VALUES (73, 111);
INSERT INTO movie.movie_genre VALUES (74, 112);
INSERT INTO movie.movie_genre VALUES (75, 112);
INSERT INTO movie.movie_genre VALUES (65, 112);
INSERT INTO movie.movie_genre VALUES (73, 112);
INSERT INTO movie.movie_genre VALUES (76, 112);
INSERT INTO movie.movie_genre VALUES (63, 113);
INSERT INTO movie.movie_genre VALUES (67, 113);
INSERT INTO movie.movie_genre VALUES (63, 114);
INSERT INTO movie.movie_genre VALUES (74, 115);
INSERT INTO movie.movie_genre VALUES (75, 115);
INSERT INTO movie.movie_genre VALUES (65, 115);
INSERT INTO movie.movie_genre VALUES (73, 115);
INSERT INTO movie.movie_genre VALUES (71, 116);
INSERT INTO movie.movie_genre VALUES (63, 116);
INSERT INTO movie.movie_genre VALUES (64, 117);
INSERT INTO movie.movie_genre VALUES (69, 117);
INSERT INTO movie.movie_genre VALUES (70, 118);
INSERT INTO movie.movie_genre VALUES (71, 118);
INSERT INTO movie.movie_genre VALUES (72, 118);
INSERT INTO movie.movie_genre VALUES (64, 118);
INSERT INTO movie.movie_genre VALUES (63, 118);
INSERT INTO movie.movie_genre VALUES (75, 119);
INSERT INTO movie.movie_genre VALUES (65, 119);
INSERT INTO movie.movie_genre VALUES (69, 119);
INSERT INTO movie.movie_genre VALUES (73, 119);
INSERT INTO movie.movie_genre VALUES (76, 119);
INSERT INTO movie.movie_genre VALUES (72, 120);
INSERT INTO movie.movie_genre VALUES (64, 120);
INSERT INTO movie.movie_genre VALUES (66, 120);
INSERT INTO movie.movie_genre VALUES (63, 120);
INSERT INTO movie.movie_genre VALUES (63, 121);
INSERT INTO movie.movie_genre VALUES (77, 122);
INSERT INTO movie.movie_genre VALUES (69, 123);
INSERT INTO movie.movie_genre VALUES (69, 124);
INSERT INTO movie.movie_genre VALUES (64, 124);
INSERT INTO movie.movie_genre VALUES (63, 125);
INSERT INTO movie.movie_genre VALUES (77, 125);
INSERT INTO movie.movie_genre VALUES (73, 125);
INSERT INTO movie.movie_genre VALUES (69, 125);
INSERT INTO movie.movie_genre VALUES (63, 126);
INSERT INTO movie.movie_genre VALUES (73, 126);
INSERT INTO movie.movie_genre VALUES (77, 126);


--
-- TOC entry 2921 (class 0 OID 16517)
-- Dependencies: 210
-- Data for Name: movie_poster; Type: TABLE DATA; Schema: movie; Owner: postgres
--

INSERT INTO movie.movie_poster VALUES (1, 'https://images-na.ssl-images-amazon.com/images/M/MV5BODU4MjU4NjIwNl5BMl5BanBnXkFtZTgwMDU2MjEyMDE@._V1._SY209_CR0,0,140,209_.jpg', 102);
INSERT INTO movie.movie_poster VALUES (2, 'https://images-na.ssl-images-amazon.com/images/M/MV5BMTUxMzQyNjA5MF5BMl5BanBnXkFtZTYwOTU2NTY3._V1._SY209_CR0,0,140,209_.jpg', 103);
INSERT INTO movie.movie_poster VALUES (3, 'https://images-na.ssl-images-amazon.com/images/M/MV5BNWIwODRlZTUtY2U3ZS00Yzg1LWJhNzYtMmZiYmEyNmU1NjMzXkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1._SY209_CR2,0,140,209_.jpg', 104);
INSERT INTO movie.movie_poster VALUES (4, 'https://images-na.ssl-images-amazon.com/images/M/MV5BNDE4OTMxMTctNmRhYy00NWE2LTg3YzItYTk3M2UwOTU5Njg4XkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1._SX140_CR0,0,140,209_.jpg', 105);
INSERT INTO movie.movie_poster VALUES (5, 'https://images-na.ssl-images-amazon.com/images/M/MV5BMTYxNDA3MDQwNl5BMl5BanBnXkFtZTcwNTU4Mzc1Nw@@._V1._SY209_CR0,0,140,209_.jpg', 106);
INSERT INTO movie.movie_poster VALUES (6, 'https://images-na.ssl-images-amazon.com/images/M/MV5BMjAxMzY3NjcxNF5BMl5BanBnXkFtZTcwNTI5OTM0Mw@@._V1._SY209_CR0,0,140,209_.jpg', 107);
INSERT INTO movie.movie_poster VALUES (7, 'https://images-na.ssl-images-amazon.com/images/M/MV5BYmJmM2Q4NmMtYThmNC00ZjRlLWEyZmItZTIwOTBlZDQ3NTQ1XkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1._SY209_CR0,0,140,209_.jpg', 108);
INSERT INTO movie.movie_poster VALUES (8, 'https://images-na.ssl-images-amazon.com/images/M/MV5BZGY5Y2RjMmItNDg5Yy00NjUwLThjMTEtNDc2OGUzNTBiYmM1XkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1._SY209_CR0,0,140,209_.jpg', 109);
INSERT INTO movie.movie_poster VALUES (9, 'https://images-na.ssl-images-amazon.com/images/M/MV5BYTUwNTdiMzMtNThmNS00ODUzLThlMDMtMTM5Y2JkNWJjOGQ2XkEyXkFqcGdeQXVyNzQ1ODk3MTQ@._V1._SX140_CR0,0,140,209_.jpg', 110);
INSERT INTO movie.movie_poster VALUES (10, 'https://images-na.ssl-images-amazon.com/images/M/MV5BYmViY2M2MTYtY2MzOS00YjQ1LWIzYmEtOTBiNjhlMGM0NjZjXkEyXkFqcGdeQXVyNDYyMDk5MTU@._V1._SX140_CR0,0,140,209_.jpg', 111);
INSERT INTO movie.movie_poster VALUES (11, 'https://images-na.ssl-images-amazon.com/images/M/MV5BOGJjNzZmMmUtMjljNC00ZjU5LWJiODQtZmEzZTU0MjBlNzgxL2ltYWdlXkEyXkFqcGdeQXVyNTAyODkwOQ@@._V1._SY209_CR0,0,140,209_.jpg', 112);
INSERT INTO movie.movie_poster VALUES (12, 'https://images-na.ssl-images-amazon.com/images/M/MV5BMDdmZGU3NDQtY2E5My00ZTliLWIzOTUtMTY4ZGI1YjdiNjk3XkEyXkFqcGdeQXVyNTA4NzY1MzY@._V1._SY209_CR0,0,140,209_.jpg', 113);
INSERT INTO movie.movie_poster VALUES (13, 'https://images-na.ssl-images-amazon.com/images/M/MV5BZjA0OWVhOTAtYWQxNi00YzNhLWI4ZjYtNjFjZTEyYjJlNDVlL2ltYWdlL2ltYWdlXkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1._SY209_CR0,0,140,209_.jpg', 114);
INSERT INTO movie.movie_poster VALUES (14, 'https://images-na.ssl-images-amazon.com/images/M/MV5BZTRhY2QwM2UtNWRlNy00ZWQwLTg3MjktZThmNjQ3NTdjN2IxXkEyXkFqcGdeQXVyMzg2MzE2OTE@._V1._SY209_CR5,0,140,209_.jpg', 115);
INSERT INTO movie.movie_poster VALUES (15, 'https://images-na.ssl-images-amazon.com/images/M/MV5BMDliMmNhNDEtODUyOS00MjNlLTgxODEtN2U3NzIxMGVkZTA1L2ltYWdlXkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1._SY209_CR0,0,140,209_.jpg', 116);
INSERT INTO movie.movie_poster VALUES (16, 'https://images-na.ssl-images-amazon.com/images/M/MV5BMTA2NDYxOGYtYjU1Mi00Y2QzLTgxMTQtMWI1MGI0ZGQ5MmU4XkEyXkFqcGdeQXVyNDk3NzU2MTQ@._V1._SY209_CR1,0,140,209_.jpg', 117);
INSERT INTO movie.movie_poster VALUES (17, 'https://images-na.ssl-images-amazon.com/images/M/MV5BMTMxNTMwODM0NF5BMl5BanBnXkFtZTcwODAyMTk2Mw@@._V1._SY209_CR0,0,140,209_.jpg', 118);
INSERT INTO movie.movie_poster VALUES (18, 'https://images-na.ssl-images-amazon.com/images/M/MV5BMjA5NDQyMjc2NF5BMl5BanBnXkFtZTcwMjg5ODcyMw@@._V1._SY209_CR0,0,140,209_.jpg', 119);
INSERT INTO movie.movie_poster VALUES (19, 'https://images-na.ssl-images-amazon.com/images/M/MV5BNjNhZTk0ZmEtNjJhMi00YzFlLWE1MmEtYzM1M2ZmMGMwMTU4XkEyXkFqcGdeQXVyNjU0OTQ0OTY@._V1._SY209_CR1,0,140,209_.jpg', 120);
INSERT INTO movie.movie_poster VALUES (20, 'https://images-na.ssl-images-amazon.com/images/M/MV5BMTc5NTk2OTU1Nl5BMl5BanBnXkFtZTcwMDc3NjAwMg@@._V1._SY209_CR0,0,140,209_.jpg', 121);
INSERT INTO movie.movie_poster VALUES (21, 'https://images-na.ssl-images-amazon.com/images/M/MV5BOTQ5NDI3MTI4MF5BMl5BanBnXkFtZTgwNDQ4ODE5MDE@._V1._SX140_CR0,0,140,209_.jpg', 122);
INSERT INTO movie.movie_poster VALUES (22, 'https://images-na.ssl-images-amazon.com/images/M/MV5BMTc5NTM5OTY0Nl5BMl5BanBnXkFtZTcwNjg1MjcyMQ@@._V1._SY209_CR3,0,140,209_.jpg', 123);
INSERT INTO movie.movie_poster VALUES (23, 'https://images-na.ssl-images-amazon.com/images/M/MV5BMjk5YmMxMjMtMTlkNi00YTI5LThhYTMtOTk2NmNiNzQwMzI0XkEyXkFqcGdeQXVyMTQ3Njg3MQ@@._V1._SX140_CR0,0,140,209_.jpg', 124);
INSERT INTO movie.movie_poster VALUES (24, 'https://images-na.ssl-images-amazon.com/images/M/MV5BMjIyNTQ5NjQ1OV5BMl5BanBnXkFtZTcwODg1MDU4OA@@._V1._SY209_CR0,0,140,209_.jpg', 125);
INSERT INTO movie.movie_poster VALUES (25, 'https://images-na.ssl-images-amazon.com/images/M/MV5BMTY3OTI5NDczN15BMl5BanBnXkFtZTcwNDA0NDY3Mw@@._V1._SX140_CR0,0,140,209_.jpg', 126);


--
-- TOC entry 2920 (class 0 OID 16498)
-- Dependencies: 209
-- Data for Name: movie_review; Type: TABLE DATA; Schema: movie; Owner: postgres
--

INSERT INTO movie.movie_review VALUES (1, 12, 'Гениальное кино! Смотришь и думаешь «Так не бывает!», но позже понимаешь, что только так и должно быть. Начинаешь заново осмысливать значение фразы, которую постоянно используешь в своей жизни, «Надежда умирает последней». Ведь если ты не надеешься, то все в твоей жизни гаснет, не остается смысла. Фильм наполнен бесконечным числом правильных афоризмов. Я уверена, что буду пересматривать его сотни раз.', 102, '2018-11-12');
INSERT INTO movie.movie_review VALUES (2, 13, 'Кино это является, безусловно, «со знаком качества». Что же до первого места в рейтинге, то, думаю, здесь имело место быть выставление «десяточек» от большинства зрителей вкупе с раздутыми восторженными откликами кинокритиков. Фильм атмосферный. Он драматичный. И, конечно, заслуживает того, чтобы находиться довольно высоко в мировом кинематографе.', 102, '2018-11-12');
INSERT INTO movie.movie_review VALUES (3, 11, 'Перестал удивляться тому, что этот фильм занимает сплошь первые места во всевозможных кино рейтингах. Особенно я люблю когда при экранизации литературного произведение из него в силу специфики кинематографа исчезает ирония и появляется некий сверхреализм, обязанный удерживать зрителя у экрана каждую отдельно взятую секунду.', 103, '2018-11-12');
INSERT INTO movie.movie_review VALUES (4, 15, 'Много еще можно сказать об этом шедевре. И то, что он учит верить, и то, чтобы никогда не сдаваться… Но самый главный девиз я увидел вот в этой фразе: «Занимайся жизнью, или занимайся смертью».', 104, '2018-11-12');
INSERT INTO movie.movie_review VALUES (5, 16, 'Очень хороший фильм, необычный сюжет, я бы даже сказала непредсказуемый. Такие фильмы уже стали редкостью.', 105, '2018-11-12');
INSERT INTO movie.movie_review VALUES (6, 19, 'У меня не найдётся слов, чтобы описать этот фильм. Не хочется быть банальной и говорить какой он отличный, непредсказуемый и т. д., но от этого никуда не деться к сожалению — ведь он ШЕДЕВРАЛЬНЫЙ!', 106, '2018-11-12');
INSERT INTO movie.movie_review VALUES (7, 17, 'Скажу сразу — фильм выглядел многообещающе, даже если не брать в расчет что он находился в топе-250 лучших фильмов. Известные голливудские актеры на главных ролях. Но нет в этом фильме должной атмосферы. Нет такого чувства что вот сейчас случится что-то страшное. Что герои попали в ситуацию из которой не смогут выбраться. В общем, не понравилось.', 106, '2018-11-12');
INSERT INTO movie.movie_review VALUES (8, 13, '«Все должно быть супер! Супер! Су-пер!» И это именно супер, ну слов других не подберешь.', 108, '2018-11-12');
INSERT INTO movie.movie_review VALUES (9, 20, 'Фильм очень красивый. Не во всем, конечно, но яркие персонажи и костюмы — это уже кое-что.', 109, '2018-11-12');
INSERT INTO movie.movie_review VALUES (10, 13, 'Этот фильм из разряда тех, что могут обеспечить хороший отдых и приподнятое настроение за счёт своей лёгкости, совсем непошлого юмора, умеренной дозы напряжения, динамики нужных скоростей.', 119, '2018-11-12');
INSERT INTO movie.movie_review VALUES (11, 15, 'Назначается Киношедевром среди развлекательных фильмов.', 119, '2018-11-12');
INSERT INTO movie.movie_review VALUES (12, 17, 'Данный кинофильм — нестареющая классика мирового кинематографа, который можно пересматривать до бесконечности и, что удивительно, он не может надоесть.', 116, '2018-11-12');
INSERT INTO movie.movie_review VALUES (13, 14, 'Рекомендую смотреть всем и не обращать внимания на надоевшее уже спасение целого мира одним человеком.', 118, '2018-11-12');
INSERT INTO movie.movie_review VALUES (14, 14, 'Удивлен. Никто не отозвался плохо? Неужели было создано произведение искусства, которое нравится всем, и которое совершенно? Нет. Может, я один такой? Фильм не вызывает во мне никаких эмоций. Неплохая сказочка. Замечательная наивная атмосфера. Местами есть забавные шутки. И, как мне показалось, этот фильм — своего рода стёб над другими боевиками. При этом превосходящий многие боевики.', 117, '2018-11-12');
INSERT INTO movie.movie_review VALUES (15, 20, 'Необыкновенно позитивный фильм. Его можно пересматривать много раз для поднятия настроения, находя много смешных, незаметных на первый взгляд моментов.', 112, '2018-11-12');
INSERT INTO movie.movie_review VALUES (16, 17, 'Легендарный. Культовый. Бессмертный. Три слова. Всего лишь три. А сколько же они выражают неподдельных эмоций и радостных впечатлений по отношению к очередному любимому и уважаемому фильму из минувшего в лету детства? Много. Слишком много. И описать эти сердечные и гарцующие в здравом рассудке чувства обыкновенными строчными предложениями иногда не представляется возможным.', 110, '2018-11-12');
INSERT INTO movie.movie_review VALUES (17, 16, 'Приятного просмотра для всех, кто не видел ещё этого шедевра больше впечатлений для тех, кто пересматривает в надцатый раз. =)', 117, '2018-11-12');
INSERT INTO movie.movie_review VALUES (18, 15, 'Это один из любимых моих фильмов с самого детства. Я видела его столько раз, что знаю практически наизусть. И могу сказать с уверенностью, что посмотрю еще не один раз.', 117, '2018-11-12');
INSERT INTO movie.movie_review VALUES (19, 13, 'Фильм, безусловно, посмотрела уже большая часть населения, которая хоть каким-то образом имеет отношение к кинематографу. Я считаю, что фильм можно пересмотреть еще не один раз.', 120, '2018-11-12');
INSERT INTO movie.movie_review VALUES (20, 18, 'Фильм продуман до мельчайших деталей. Идеальный фильм для улучшения настроения, единственный в своем роде. Обязателен к просмотру!', 119, '2018-11-12');
INSERT INTO movie.movie_review VALUES (21, 15, 'Фильм потрясающий, в нем хватает абсолютно всего: и драк, и музыки, и юмора, и любви.', 124, '2018-11-12');
INSERT INTO movie.movie_review VALUES (22, 11, 'У фильма есть свои мелкие недостатки  и неточности, но многочисленные достоинства в несколько раз перевешивают. Много вдохновляющего креатива!', 121, '2018-11-12');
INSERT INTO movie.movie_review VALUES (23, 12, 'Хоть и не по возрасту мне заводить скрипучую пластинку с мелодией «Раньше и деревья были выше, и трава зеленее…», а хочется. Выражать свою любовь к настолько близкому произведению крайне сложно.', 120, '2018-11-12');
INSERT INTO movie.movie_review VALUES (24, 18, 'Вердикт: прекрасная, нестареющая классика, которая рекомендована мною для всех.', 124, '2018-11-12');
INSERT INTO movie.movie_review VALUES (25, 14, 'Для воскресного вечернего просмотра подходит по всем критериям.', 122, '2018-11-12');
INSERT INTO movie.movie_review VALUES (26, 15, 'Хороший и по-настоящему интересный фильм, с хорошим сюжетом и неплохим музыкальным сопровождением. Всем советую к просмотру.', 123, '2018-11-12');
INSERT INTO movie.movie_review VALUES (27, 16, 'Полагаю, этот фильм должен быть в коллекции каждого уважающего себя киномана.', 125, '2018-11-12');
INSERT INTO movie.movie_review VALUES (28, 17, 'Нетленный шедевр мирового кинематографа, который можно пересматривать десятки раз и получать все такой — же, извините за выражение, кайф от просмотра. Минусы у фильма, конечно, есть, но черт возьми. Их просто не хочется замечать! Ты настолько поглощен просмотром фильма, что просто не хочется придираться к разным мелочам.', 126, '2018-11-12');
INSERT INTO movie.movie_review VALUES (29, 18, 'Фильм только выигрывает от частого просмотра и всегда поднимает мне настроение (наряду с драмой, тут еще и тонкий юмор).', 125, '2018-11-12');
INSERT INTO movie.movie_review VALUES (30, 19, 'Конечно, бесспорно культовый фильм, не реалистичный, наивный, где то глупый, но такой же увлекательный и удивительный, как и много лет назад', 123, '2018-11-12');
INSERT INTO movie.movie_review VALUES (31, 19, 'В итоге мы имеем отличный представитель своего жанра, который прошёл проверку временем и до сих пор отлично смотрится, несмотря на классический сюжет', 113, '2018-11-12');
INSERT INTO movie.movie_review VALUES (32, 20, 'Скажу только одно — как я жалею, что не посмотрела его раньше!', 114, '2018-11-12');


--
-- TOC entry 2919 (class 0 OID 16487)
-- Dependencies: 208
-- Data for Name: user; Type: TABLE DATA; Schema: movie; Owner: postgres
--

INSERT INTO movie."user" VALUES (17, 'Амелия Кэннеди', 'amelia.kennedy58@example.com', 'beaker', 'zlodyjvocg', 'ca431020cc78b5cc285066dcb4d8973a');
INSERT INTO movie."user" VALUES (18, 'Айда Дэвис', 'ida.davis80@example.com', 'pepsi1', 'vinqxbqbqt', '53f91d99b9c789e0f02d192e4f61430d');
INSERT INTO movie."user" VALUES (19, 'Джесси Паттерсон', 'jessie.patterson68@example.com', 'tommy', 'fckvqaijwu', 'c4985368f98fd58cd71e22eb21aa1599');
INSERT INTO movie."user" VALUES (20, 'Деннис Крейг', 'dennis.craig82@example.com', 'coldbeer', 'qpvjnzlwgq', 'f71282eaedba8502f6ebe4bedac556ac');
INSERT INTO movie."user" VALUES (12, 'Дарлин Эдвардс', 'darlene.edwards15@example.com', 'bricks', 'yehseqhlrq', '8d75c25144926618cd7a015e63b84d8b');
INSERT INTO movie."user" VALUES (11, 'Рональд Рейнольдс', 'ronald.reynolds66@example.com', 'paco', 'zciteqsyva', '578f6be7aa55d65e7f9c5a6693b6ae73');
INSERT INTO movie."user" VALUES (13, 'Габриэль Джексон', 'gabriel.jackson91@example.com', 'hjkl', 'nldpvccbgu', '37896688177098b567344aa40eec353f');
INSERT INTO movie."user" VALUES (14, 'Дэрил Брайант', 'daryl.bryant94@example.com', 'exodus', 'nyyiztkvzs', '3e87a86bc16cd934c2ea692f6f98ccd1');
INSERT INTO movie."user" VALUES (15, 'Нил Паркер', 'neil.parker43@example.com', '878787', 'xhymxmctxi', '9e59ea75b4efba8933a3ee255f235b60');
INSERT INTO movie."user" VALUES (16, 'Трэвис Райт', 'travis.wright36@example.com', 'smart', 'lhijuithxz', 'b69d60a59739635fd8b271a702b3833f');


--
-- TOC entry 2934 (class 0 OID 0)
-- Dependencies: 213
-- Name: country_cntr_id_seq; Type: SEQUENCE SET; Schema: movie; Owner: postgres
--

SELECT pg_catalog.setval('movie.country_cntr_id_seq', 45, true);


--
-- TOC entry 2935 (class 0 OID 0)
-- Dependencies: 211
-- Name: genre_gnr_id_seq; Type: SEQUENCE SET; Schema: movie; Owner: postgres
--

SELECT pg_catalog.setval('movie.genre_gnr_id_seq', 77, true);


--
-- TOC entry 2936 (class 0 OID 0)
-- Dependencies: 212
-- Name: movie_m_id_seq; Type: SEQUENCE SET; Schema: movie; Owner: postgres
--

SELECT pg_catalog.setval('movie.movie_m_id_seq', 126, true);


--
-- TOC entry 2937 (class 0 OID 0)
-- Dependencies: 214
-- Name: movie_poster_poster_id_seq; Type: SEQUENCE SET; Schema: movie; Owner: postgres
--

SELECT pg_catalog.setval('movie.movie_poster_poster_id_seq', 25, true);


--
-- TOC entry 2938 (class 0 OID 0)
-- Dependencies: 215
-- Name: movie_review_review_id_seq; Type: SEQUENCE SET; Schema: movie; Owner: postgres
--

SELECT pg_catalog.setval('movie.movie_review_review_id_seq', 32, true);


--
-- TOC entry 2939 (class 0 OID 0)
-- Dependencies: 216
-- Name: user_usr_id_seq; Type: SEQUENCE SET; Schema: movie; Owner: postgres
--

SELECT pg_catalog.setval('movie.user_usr_id_seq', 20, true);


--
-- TOC entry 2774 (class 2606 OID 16476)
-- Name: movie_country MOVIE_COUNTRY_PK; Type: CONSTRAINT; Schema: movie; Owner: postgres
--

ALTER TABLE ONLY movie.movie_country
    ADD CONSTRAINT "MOVIE_COUNTRY_PK" PRIMARY KEY (m_id, cntr_id);


--
-- TOC entry 2780 (class 2606 OID 16524)
-- Name: movie_poster POSTER_ID_PK; Type: CONSTRAINT; Schema: movie; Owner: postgres
--

ALTER TABLE ONLY movie.movie_poster
    ADD CONSTRAINT "POSTER_ID_PK" PRIMARY KEY (poster_id);


--
-- TOC entry 2778 (class 2606 OID 16506)
-- Name: movie_review REVIEW_PK; Type: CONSTRAINT; Schema: movie; Owner: postgres
--

ALTER TABLE ONLY movie.movie_review
    ADD CONSTRAINT "REVIEW_PK" PRIMARY KEY (review_id);


--
-- TOC entry 2766 (class 2606 OID 16437)
-- Name: country country_pk; Type: CONSTRAINT; Schema: movie; Owner: postgres
--

ALTER TABLE ONLY movie.country
    ADD CONSTRAINT country_pk PRIMARY KEY (cntr_id);


--
-- TOC entry 2768 (class 2606 OID 16450)
-- Name: genre genre_pk; Type: CONSTRAINT; Schema: movie; Owner: postgres
--

ALTER TABLE ONLY movie.genre
    ADD CONSTRAINT genre_pk PRIMARY KEY (gnr_id);


--
-- TOC entry 2770 (class 2606 OID 16565)
-- Name: genre genre_uk; Type: CONSTRAINT; Schema: movie; Owner: postgres
--

ALTER TABLE ONLY movie.genre
    ADD CONSTRAINT genre_uk UNIQUE (gnr_name);


--
-- TOC entry 2772 (class 2606 OID 16458)
-- Name: movie_genre movie_genre_pk; Type: CONSTRAINT; Schema: movie; Owner: postgres
--

ALTER TABLE ONLY movie.movie_genre
    ADD CONSTRAINT movie_genre_pk PRIMARY KEY (gnr_id, m_id);


--
-- TOC entry 2764 (class 2606 OID 16429)
-- Name: movie movie_pk; Type: CONSTRAINT; Schema: movie; Owner: postgres
--

ALTER TABLE ONLY movie.movie
    ADD CONSTRAINT movie_pk PRIMARY KEY (m_id);


--
-- TOC entry 2776 (class 2606 OID 16494)
-- Name: user user_pkey; Type: CONSTRAINT; Schema: movie; Owner: postgres
--

ALTER TABLE ONLY movie."user"
    ADD CONSTRAINT user_pkey PRIMARY KEY (usr_id);


--
-- TOC entry 2784 (class 2606 OID 16482)
-- Name: movie_country COUNTRY_ID_FK; Type: FK CONSTRAINT; Schema: movie; Owner: postgres
--

ALTER TABLE ONLY movie.movie_country
    ADD CONSTRAINT "COUNTRY_ID_FK" FOREIGN KEY (cntr_id) REFERENCES movie.country(cntr_id);


--
-- TOC entry 2782 (class 2606 OID 16464)
-- Name: movie_genre GNR_ID_FK; Type: FK CONSTRAINT; Schema: movie; Owner: postgres
--

ALTER TABLE ONLY movie.movie_genre
    ADD CONSTRAINT "GNR_ID_FK" FOREIGN KEY (gnr_id) REFERENCES movie.genre(gnr_id);


--
-- TOC entry 2783 (class 2606 OID 16477)
-- Name: movie_country MOVIE_ID_FK; Type: FK CONSTRAINT; Schema: movie; Owner: postgres
--

ALTER TABLE ONLY movie.movie_country
    ADD CONSTRAINT "MOVIE_ID_FK" FOREIGN KEY (m_id) REFERENCES movie.movie(m_id);


--
-- TOC entry 2781 (class 2606 OID 16459)
-- Name: movie_genre M_ID_FK; Type: FK CONSTRAINT; Schema: movie; Owner: postgres
--

ALTER TABLE ONLY movie.movie_genre
    ADD CONSTRAINT "M_ID_FK" FOREIGN KEY (m_id) REFERENCES movie.movie(m_id);


--
-- TOC entry 2787 (class 2606 OID 16525)
-- Name: movie_poster POSTER_M_ID_FK; Type: FK CONSTRAINT; Schema: movie; Owner: postgres
--

ALTER TABLE ONLY movie.movie_poster
    ADD CONSTRAINT "POSTER_M_ID_FK" FOREIGN KEY (m_id) REFERENCES movie.movie(m_id);


--
-- TOC entry 2785 (class 2606 OID 16512)
-- Name: movie_review USR_ID_FK; Type: FK CONSTRAINT; Schema: movie; Owner: postgres
--

ALTER TABLE ONLY movie.movie_review
    ADD CONSTRAINT "USR_ID_FK" FOREIGN KEY (usr_id) REFERENCES movie."user"(usr_id);


--
-- TOC entry 2786 (class 2606 OID 16507)
-- Name: movie_review none; Type: FK CONSTRAINT; Schema: movie; Owner: postgres
--

ALTER TABLE ONLY movie.movie_review
    ADD CONSTRAINT "none" FOREIGN KEY (m_id) REFERENCES movie.movie(m_id);


-- Completed on 2021-01-31 21:31:47

--
-- PostgreSQL database dump complete
--

