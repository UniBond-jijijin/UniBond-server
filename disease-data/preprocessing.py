# import mysql.connector
import os
from dotenv import load_dotenv
import pandas as pd
import datetime
from sqlalchemy import create_engine

load_dotenv()

MYSQL_USER = os.getenv("MYSQL_USER")
MYSQL_PW = os.getenv("MYSQL_PW")
MYSQL_ENDPOINT = os.getenv("MYSQL_ENDPOINT")
TABLE_NAME = "disease"

engine = create_engine(f"mysql+pymysql://{MYSQL_USER}:{MYSQL_PW}@{MYSQL_ENDPOINT}")
connection = engine.connect()

df = pd.read_csv("./csv-data/rare_disease_2023_10_02_13_49_53.csv", encoding='utf-8')

df = df[['질환명(한글)', '질환명(영문)']]

df.rename(columns = {'질환명(한글)':'disease_name_kor','질환명(영문)':'disease_name_eng'},inplace=True)
df['created_date'] = datetime.datetime.now().strftime("%Y-%m-%dT%H:%M:%S.%f")
df['last_modified_date'] = df['created_date']
df['status'] = "ACTIVE"

df = df[['created_date', 'last_modified_date', 'disease_name_eng', 'disease_name_kor', 'status']]
df.to_sql(name=TABLE_NAME,
          con=connection,
          if_exists='append', # or 'replace'
          index=False) 

