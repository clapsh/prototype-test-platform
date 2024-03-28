# 모듈 import
import pymysql

def connDb(querys):
    # MySQL 데이터베이스 연결
    db = pymysql.connect(host='127.0.0.1', user='root', password='1234', db='shop', charset='utf8')

    try:
        for q in querys:
            cursor = db.cursor()
            cursor.execute(q)
            # db 데이터 가져오기
            #cursor.fetchall() #모든 행 가져오기
            cursor.fetchone() # 하나의 행만 가져오기
            #cursor.fetchmany(n) # n개의 데이터 가져오기 
            cursor.close()
        db.commit()
    except Exception as e:
        print(str(e))
        db.rollback()
    
    # Database 닫기
    db.close()