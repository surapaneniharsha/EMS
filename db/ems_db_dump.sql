--
-- PostgreSQL database dump
--

\restrict YwUiL8n52xYvVOHiPhCo9G7ynvptRUNM3T02iNPufojQ7GjeoAcJnXBbzh80Zdw

-- Dumped from database version 18.3 (Debian 18.3-1.pgdg13+1)
-- Dumped by pg_dump version 18.3 (Debian 18.3-1.pgdg13+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: gender_enum; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.gender_enum AS ENUM (
    'MALE',
    'FEMALE',
    'OTHERS'
);


ALTER TYPE public.gender_enum OWNER TO postgres;

--
-- Name: marital_enum; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.marital_enum AS ENUM (
    'MARRIED',
    'SINGLE'
);


ALTER TYPE public.marital_enum OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: department; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.department (
    dept_id integer NOT NULL,
    dept_name character varying(100) NOT NULL,
    location character varying(100)
);


ALTER TABLE public.department OWNER TO postgres;

--
-- Name: department_dept_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.department_dept_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.department_dept_id_seq OWNER TO postgres;

--
-- Name: department_dept_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.department_dept_id_seq OWNED BY public.department.dept_id;


--
-- Name: employee; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.employee (
    emp_id integer NOT NULL,
    first_name character varying(50) NOT NULL,
    last_name character varying(50) NOT NULL,
    phone character varying(15) NOT NULL,
    email character varying(254) NOT NULL,
    gender public.gender_enum NOT NULL,
    national_id character varying(20) NOT NULL,
    passport_id character varying(15),
    citizenship character(2),
    marital_status public.marital_enum NOT NULL,
    blood_group character varying(3),
    identity_marks character varying(75) NOT NULL,
    salary integer NOT NULL,
    job_title character varying(50) NOT NULL,
    disability boolean DEFAULT false,
    bank_name character varying(75),
    acc_no character varying(34),
    dept_id integer
);


ALTER TABLE public.employee OWNER TO postgres;

--
-- Name: employee_emp_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.employee_emp_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.employee_emp_id_seq OWNER TO postgres;

--
-- Name: employee_emp_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.employee_emp_id_seq OWNED BY public.employee.emp_id;


--
-- Name: department dept_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.department ALTER COLUMN dept_id SET DEFAULT nextval('public.department_dept_id_seq'::regclass);


--
-- Name: employee emp_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.employee ALTER COLUMN emp_id SET DEFAULT nextval('public.employee_emp_id_seq'::regclass);


--
-- Data for Name: department; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.department (dept_id, dept_name, location) FROM stdin;
1	Human Resources	New York
2	Finance	London
3	Engineering	San Francisco
4	Marketing	Chicago
5	Sales	Berlin
6	Operations	Tokyo
7	Research	Boston
8	IT Support	Sydney
9	Legal	Toronto
10	Customer Service	Dubai
\.


--
-- Data for Name: employee; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.employee (emp_id, first_name, last_name, phone, email, gender, national_id, passport_id, citizenship, marital_status, blood_group, identity_marks, salary, job_title, disability, bank_name, acc_no, dept_id) FROM stdin;
1	John	Doe	555-0100	john.doe@example.com	MALE	NID1001	P1001	US	SINGLE	O+	Mole on cheek	70000	HR Manager	f	Bank of America	US123456789	1
2	Anna	Clark	555-0101	anna.clark@example.com	FEMALE	NID1002	P1002	US	MARRIED	A+	Tattoo on wrist	65000	HR Specialist	f	Bank of America	US223456789	1
3	Robert	Hall	555-0102	robert.hall@example.com	MALE	NID1003	P1003	US	SINGLE	B+	Scar on hand	60000	Recruiter	f	Bank of America	US323456789	1
4	Laura	Young	555-0103	laura.young@example.com	FEMALE	NID1004	P1004	US	MARRIED	AB+	Mole on arm	62000	HR Coordinator	f	Bank of America	US423456789	1
5	David	King	555-0104	david.king@example.com	MALE	NID1005	P1005	US	SINGLE	O-	Birthmark on neck	58000	HR Assistant	f	Bank of America	US523456789	1
6	Jane	Smith	555-0105	jane.smith@example.com	FEMALE	NID1006	P1006	GB	MARRIED	A+	Scar on hand	80000	Finance Analyst	f	HSBC	GB987654321	2
7	Paul	Wright	555-0106	paul.wright@example.com	MALE	NID1007	P1007	GB	SINGLE	B+	Mole on cheek	75000	Accountant	f	HSBC	GB876543210	2
8	Emily	Scott	555-0107	emily.scott@example.com	FEMALE	NID1008	P1008	GB	MARRIED	AB+	Tattoo on arm	72000	Finance Coordinator	f	HSBC	GB765432109	2
9	Mark	Evans	555-0108	mark.evans@example.com	MALE	NID1009	P1009	GB	SINGLE	O-	Scar on forehead	71000	Budget Analyst	f	HSBC	GB654321098	2
10	Sophia	Morris	555-0110	sophia.morris@example.com	FEMALE	NID1010	P1010	GB	MARRIED	A-	Mole on hand	68000	Financial Auditor	f	HSBC	GB543210987	2
11	Michael	Brown	555-0111	michael.brown@example.com	MALE	NID1011	P1011	US	SINGLE	B+	Tattoo on arm	95000	Software Engineer	f	Chase Bank	US234567890	3
12	Linda	Green	555-0112	linda.green@example.com	FEMALE	NID1012	P1012	US	MARRIED	O+	Scar on neck	90000	Frontend Developer	f	Chase Bank	US334567890	3
13	Kevin	Baker	555-0113	kevin.baker@example.com	MALE	NID1013	P1013	US	SINGLE	AB+	Mole on hand	88000	Backend Developer	f	Chase Bank	US434567890	3
14	Rachel	Adams	555-0114	rachel.adams@example.com	FEMALE	NID1014	P1014	US	MARRIED	A+	Tattoo on leg	87000	DevOps Engineer	f	Chase Bank	US534567890	3
15	Brian	Mitchell	555-0115	brian.mitchell@example.com	MALE	NID1015	P1015	US	SINGLE	B-	Scar on arm	86000	QA Engineer	f	Chase Bank	US634567890	3
16	Emily	Davis	555-0116	emily.davis@example.com	FEMALE	NID1016	P1016	CA	MARRIED	AB+	Birthmark on neck	65000	Marketing Specialist	f	Royal Bank of Canada	CA345678901	4
17	Daniel	Harris	555-0117	daniel.harris@example.com	MALE	NID1017	P1017	CA	SINGLE	O+	Mole on chin	63000	SEO Analyst	f	Royal Bank of Canada	CA445678901	4
18	Grace	Robinson	555-0118	grace.robinson@example.com	FEMALE	NID1018	P1018	CA	MARRIED	A+	Scar on hand	62000	Content Strategist	f	Royal Bank of Canada	CA545678901	4
19	Jason	Walker	555-0119	jason.walker@example.com	MALE	NID1019	P1019	CA	SINGLE	B+	Tattoo on arm	61000	Marketing Coordinator	f	Royal Bank of Canada	CA645678901	4
20	Olivia	White	555-0120	olivia.white@example.com	FEMALE	NID1020	P1020	CA	MARRIED	AB-	Birthmark on hand	60000	Social Media Manager	f	Royal Bank of Canada	CA745678901	4
\.


--
-- Name: department_dept_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.department_dept_id_seq', 10, true);


--
-- Name: employee_emp_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.employee_emp_id_seq', 20, true);


--
-- Name: department department_dept_name_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.department
    ADD CONSTRAINT department_dept_name_key UNIQUE (dept_name);


--
-- Name: department department_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.department
    ADD CONSTRAINT department_pkey PRIMARY KEY (dept_id);


--
-- Name: employee employee_email_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.employee
    ADD CONSTRAINT employee_email_key UNIQUE (email);


--
-- Name: employee employee_national_id_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.employee
    ADD CONSTRAINT employee_national_id_key UNIQUE (national_id);


--
-- Name: employee employee_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.employee
    ADD CONSTRAINT employee_pkey PRIMARY KEY (emp_id);


--
-- Name: employee fk_department; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.employee
    ADD CONSTRAINT fk_department FOREIGN KEY (dept_id) REFERENCES public.department(dept_id) ON DELETE SET NULL;


--
-- PostgreSQL database dump complete
--

\unrestrict YwUiL8n52xYvVOHiPhCo9G7ynvptRUNM3T02iNPufojQ7GjeoAcJnXBbzh80Zdw

