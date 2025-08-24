#!/usr/bin/env node
/**
 * @FileName : DBPoolHelper.js
 * @Description : 데이터베이스 커텍션 풀 관리 클래스
 * @Author : Lee Kwang-Ho (leekh4232@gmail.com)
 */

import mysql from "mysql2/promise";
/**
 * DATABASE Connection Pool을 관리하기 위한 SingleTon 클래스
 */
class DBPoolHelper {
    // 싱글톤 객체
    static #current = null;
    connectionInfo = null;

    /** 싱글톤 객체를 생성하여 리턴하는 메서드 */
    static getInstance(connectionInfo) {
        if (DBPoolHelper.#current == null) {
            DBPoolHelper.#current = new DBPoolHelper(connectionInfo);
        }
        return DBPoolHelper.#current;
    }

    constructor(connectionInfo) {
        // Connection pool 객체를 멤버변수로서 생성
        this.pool = mysql.createPool(connectionInfo);

        // 데이터베이스에 접속된 경우 발생할 이벤트
        this.pool.on('connection', (connection) => {
            // const oldQuery = connection.query;

            // connection.query = function (...args) {
            //     const queryCmd = oldQuery.apply(connection, args);
            //     const sql = queryCmd.sql.trim().replace(/\s\s+/g, ' ').replace(/[\r\n]+/g," [LF] ");
            //     logger.debug(`${sql}`);
            //     return queryCmd;
            // }
        });

        this.pool.on('acquire', (connection) => {
            //logger.info(` >> Connection pool activate [threaId=${connection.threadId}]`);
        });

        this.pool.on('release', (connection) => {
            //logger.info(` >> Connection pool deactivate [threaId=${connection.threadId}]`);
        });
    }

    /**
     * Connection Pool에서 하나의 데이터베이스 접속 객체를 임대하는 메서드
     */
    async getConnection() {
        let dbcon = null;

        try {
            dbcon = await this.pool.getConnection();
        } catch (err) {
            // 임대한 자원이 있다면 반드시 반납해야 함.
            if (dbcon) { dbcon.release(); }
            console.error(err);
            throw err;
        }

        return dbcon;
    }

    /**
     * 데이터베이스 커넥션 풀을 종료함
     */
    close() {
        this.pool.end();
    }
}

// 싱글톤 객체를 모듈로 내보냄
export default DBPoolHelper;
