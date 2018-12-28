package com.gome.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * @author azq
 * @createtime 2018年12月18日
 */
public class MoneyDao {
	private static Logger log = Logger.getLogger(MoneyDao.class);

	// 属性
	private Statement stm;
	private PreparedStatement ps;
	private ResultSet rs;

	/**
	 * 查询表中所有元素
	 * 
	 * @return
	 */
	public List<Object[]> queryAllDataByDay(Connection conn) {
		String sql = "select a.service_num,a.status_chg_type,a.join_time,b.active_time,b.inactive_time,a.deafeat_desc,a.serious_id from arch_detail_comm a ,gn_policy_comm b ,Agent_rule_comm c where a.product_id=b.product_id and b.policy_id=c.policy_id";
		List<Object[]> list = new ArrayList<Object[]>();
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			int count = rs.getMetaData().getColumnCount();
			System.out.println(count);
			while (rs.next()) {

				Object[] obj = new Object[count];
				for (int i = 0; i < count; i++) {
					obj[i] = rs.getString(i + 1);

				}
				list.add(obj);
			}
		} catch (SQLException e) {
			log.error("******query all phone info is error ,this is *****", e);

		} finally {

			closeResultSet();
			closeStatement();
			closeConnection(conn);
		}

		return list;
	}
	
	public  List<Object[]> queryMoneyByPhone(Connection conn){
		

String sql = "select a.service_num,a.status_chg_type,a.join_time,b.active_time,b.inactive_time,a.deafeat_desc,a.serious_id from arch_detail_comm a ,gn_policy_comm b ,Agent_rule_comm c where a.product_id=b.product_id and b.policy_id=c.policy_id";
		List<Object[]> list = new ArrayList<Object[]>();
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			int count = rs.getMetaData().getColumnCount();
			System.out.println(count);
			while (rs.next()) {

				Object[] obj = new Object[count];
				for (int i = 0; i < count; i++) {
					obj[i] = rs.getString(i + 1);

				}
				list.add(obj);
			}
		} catch (SQLException e) {
			log.error("******query all phone info is error ,this is *****", e);

		} finally {

			closeResultSet();
			closeStatement();
			closeConnection(conn);
		}

		return list;
	}

	/**
	 * 根据手机号码查询之前有无出现过计算费用的号码
	 * 
	 * @param PhoneNum
	 * @param conn
	 * @return
	 */
	public int queryCountByPhone(String PhoneNum, Connection conn) {
		log.info("query one phonenum=" + PhoneNum);
		String sql = "select count(1) from arch_detail_comm where service_num =?";
		int num = 0;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, PhoneNum);
			rs = ps.executeQuery();
			rs.next();
			num = rs.getInt(1);
		} catch (SQLException e) {
			log.error("******query serivce_nunm count is error ,this is *****", e);
		}finally {

			closeResultSet();
			closeStatement();
			closeConnection(conn);
		}


		return num;
	}
	
	

	/**
	 * 回写号码状态，校验失败原因
	 * 
	 * @param beizhu
	 * @param phone
	 * @param time
	 * @param conn
	 * @return
	 */
	public int updateDataByOne(String beizhu, String phone, String serious_id, int flag, Connection conn) {
		String sql = "update arch_detail_comm a set a.deafeat_desc=?,state=? where serious_id =? and service_num=? ";
		int result = 0;
		try {
			ps = conn.prepareStatement(sql);
			ps.setObject(1, beizhu);
			ps.setObject(4, phone);
			ps.setObject(3, serious_id);
			ps.setInt(2, flag);
			result = ps.executeUpdate();

		} catch (SQLException e) {
			log.error("******update remark info  is error ,this is *****", e);
		} finally {

			closeResultSet();
			closeStatement();
			closeConnection(conn);
		}

		return result;
	}

	// 关闭ResultSet
	public void closeResultSet() {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				log.error("******close result set *****", e);
			}
		}
	}

	// 关闭Connection
	public void closeConnection(Connection conn) {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				log.error("******close conn catch *****", e);
			}
		}
	}

	// 关闭Statement和PreparedStatement
	public void closeStatement() {
		if (stm != null) {
			try {
				stm.close();
			} catch (SQLException e) {
				e.printStackTrace();
				log.error("******close statement and preparedStatement catch *****", e);
			}
		}

		if (ps != null) {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
