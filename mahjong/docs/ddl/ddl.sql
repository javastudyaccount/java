--
-- PostgreSQL database dump
--

-- Dumped from database version 12.4
-- Dumped by pg_dump version 13.2

-- Started on 2022-02-19 03:58:56 UTC

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

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 202 (class 1259 OID 228779)
-- Name: game; Type: TABLE; Schema: public; Owner: game
--
drop table public.game cascade;
CREATE TABLE public.game (
    game_id serial NOT NULL,
    roome_id bigint NOT NULL,
    started_timestamp time without time zone NOT NULL,
    ended_timestamp time without time zone,
    shuffled_tiles json,
    from_direction character varying(20),
    from_column integer,
    deleted_flg boolean DEFAULT false NOT NULL,
    created_timestamp time without time zone NOT NULL,
    created_user character varying(20) NOT NULL,
    updated_timestamp time without time zone NOT NULL,
    updated_user character varying(20)
);


ALTER TABLE public.game OWNER TO game;

--
-- TOC entry 203 (class 1259 OID 228786)
-- Name: game_log; Type: TABLE; Schema: public; Owner: game
--
drop table public.game_log cascade;
CREATE TABLE public.game_log (
    game_log_id serial NOT NULL,
    game_id bigint NOT NULL,
    player_id bigint NOT NULL,
    operation character varying(20) NOT NULL,
    tiles json NOT NULL,
    player_id_counterpart bigint,
    deleted_flg boolean DEFAULT false NOT NULL,
    created_timestamp time without time zone NOT NULL,
    created_user character varying(20) NOT NULL,
    updated_timestamp time without time zone NOT NULL,
    updated_user character varying(20) NOT NULL
);


ALTER TABLE public.game_log OWNER TO game;

ALTER TABLE public.game_log_game_log_id_seq OWNER TO game;

--
-- TOC entry 2992 (class 0 OID 0)
-- Dependencies: 204
-- Name: game_log_game_log_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: game
--

ALTER SEQUENCE public.game_log_game_log_id_seq OWNED BY public.game_log.game_log_id;


--
-- TOC entry 205 (class 1259 OID 228795)
-- Name: game_player; Type: TABLE; Schema: public; Owner: game
--
drop table public.game_player cascade;
CREATE TABLE public.game_player (
    game_id bigint NOT NULL,
    player_id bigint NOT NULL,
    mentsu json,
    last integer,
    direction character varying(20) NOT NULL,
    is_east boolean NOT NULL,
    deleted_flg boolean DEFAULT false NOT NULL,
    created_timestamp time without time zone NOT NULL,
    created_user character varying(20) NOT NULL,
    updated_timestamp time without time zone NOT NULL,
    updated_user character varying(20) NOT NULL
);


ALTER TABLE public.game_player OWNER TO game;

--
-- TOC entry 206 (class 1259 OID 228802)
-- Name: player; Type: TABLE; Schema: public; Owner: game
--
drop table public.player cascade;
CREATE TABLE public.player (
    player_id serial NOT NULL,
    nickename character varying(20) NOT NULL,
    deleted_flg boolean DEFAULT false NOT NULL,
    created_timestamp time without time zone NOT NULL,
    created_user character varying(20) NOT NULL,
    updated_timestamp time without time zone NOT NULL,
    updated_user character varying(20) NOT NULL
);


ALTER TABLE public.player OWNER TO game;

ALTER TABLE public.player_player_id_seq OWNER TO game;

--
-- TOC entry 2993 (class 0 OID 0)
-- Dependencies: 207
-- Name: player_player_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: game
--

ALTER SEQUENCE public.player_player_id_seq OWNED BY public.player.player_id;


--
-- TOC entry 208 (class 1259 OID 228808)
-- Name: room; Type: TABLE; Schema: public; Owner: game
--
drop table public.room cascade;
CREATE TABLE public.room (
    room_id serial NOT NULL,
    room_name character varying(50) NOT NULL,
    deleted_flg boolean DEFAULT false NOT NULL,
    created_timestamp timestamp without time zone NOT NULL,
    created_user character varying(20) NOT NULL,
    updated_timestamp timestamp without time zone NOT NULL,
    updated_user character varying(20) NOT NULL
);


ALTER TABLE public.room OWNER TO game;

ALTER TABLE public.room_room_id_seq OWNER TO game;

--
-- TOC entry 2994 (class 0 OID 0)
-- Dependencies: 209
-- Name: room_room_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: game
--

ALTER SEQUENCE public.room_room_id_seq OWNED BY public.room.room_id;


--
-- TOC entry 2849 (class 2604 OID 228814)
-- Name: game_log game_log_id; Type: DEFAULT; Schema: public; Owner: game
--

ALTER TABLE ONLY public.game_log ALTER COLUMN game_log_id SET DEFAULT nextval('public.game_log_game_log_id_seq'::regclass);


--
-- TOC entry 2852 (class 2604 OID 228815)
-- Name: player player_id; Type: DEFAULT; Schema: public; Owner: game
--

ALTER TABLE ONLY public.player ALTER COLUMN player_id SET DEFAULT nextval('public.player_player_id_seq'::regclass);


--
-- TOC entry 2854 (class 2604 OID 228816)
-- Name: room room_id; Type: DEFAULT; Schema: public; Owner: game
--

ALTER TABLE ONLY public.room ALTER COLUMN room_id SET DEFAULT nextval('public.room_room_id_seq'::regclass);


--
-- TOC entry 2856 (class 2606 OID 228818)
-- Name: game game_pkey; Type: CONSTRAINT; Schema: public; Owner: game
--

ALTER TABLE ONLY public.game
    ADD CONSTRAINT game_pkey PRIMARY KEY (game_id);


--
-- TOC entry 2858 (class 2606 OID 228820)
-- Name: player player_pkey; Type: CONSTRAINT; Schema: public; Owner: game
--

ALTER TABLE ONLY public.player
    ADD CONSTRAINT player_pkey PRIMARY KEY (player_id);


--
-- TOC entry 2860 (class 2606 OID 228822)
-- Name: room room_pkey; Type: CONSTRAINT; Schema: public; Owner: game
--

ALTER TABLE ONLY public.room
    ADD CONSTRAINT room_pkey PRIMARY KEY (room_id);


-- Completed on 2022-02-19 03:58:56 UTC

--
-- PostgreSQL database dump complete
--

