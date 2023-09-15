package com.jdbc.demo.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import com.jdbc.demo.model.Response;
import com.jdbc.demo.model.SignUpModel;
import com.jdbc.demo.service.SignUpService;

@Component
public class SignUpDao implements SignUpService {

	Response rsp = new Response();

	String url = "jdbc:mysql://127.0.0.1:3306/kgm";
	String username = "root";
	String pwd = "Ajithmanas@1997";

	public Response createUser(SignUpModel values) {

		String uuid = UUID.randomUUID().toString();
		values.setsNo(uuid);
		values.setCreatedBy(uuid);
		values.setUpdatedBy(uuid);

		Date date = new Date(Calendar.getInstance().getTime().getTime());
		values.setCreatedDate(date);
		values.setUpdatedDate(date);

		values.setActive(true);

		try {

			Class.forName("com.mysql.cj.jdbc.Driver");

			try (Connection conn = DriverManager.getConnection(url, username, pwd);
					Statement st = conn.createStatement();) {

				if (String.valueOf(values.getPhoneNo()).length() == 10 && values.getPhoneNo() >= 6000000000l
						&& values.getPhoneNo() <= 9999999999l) {

					String email = "^(.+)@gmail.com$";
					Pattern emailPattern = Pattern.compile(email);
					Matcher emailMatch = emailPattern.matcher(values.getEmail());

					if (emailMatch.matches() == true) {

						String insertQuery = "INSERT INTO kgm.user_details(s_no,first_name,last_name,date_of_birth,gender,phone_no,email,password,created_by,updated_by,created_date,updated_date,is_active)"
								+ "VALUES('" + values.getsNo() + "','" + values.getFirstName() + "','"
								+ values.getLastName() + "','" + values.getDateOfBirth() + "','" + values.getGender()
								+ "'," + values.getPhoneNo() + ",'" + values.getEmail() + "','" + values.getPassword()
								+ "','" + values.getCreatedBy() + "','" + values.getUpdatedBy() + "','"
								+ values.getCreatedDate() + "','" + values.getUpdatedDate() + "','" + values.isActive()
								+ "');";

						System.out.println(insertQuery);

						st.executeUpdate(insertQuery);

						rsp.setData("User Created Successfully");
						rsp.setResponseCode(200);
						rsp.setResponseMessage("Success");

					} else {
						rsp.setData("Email Incorrect!");
						rsp.setResponseCode(500);
						rsp.setResponseMessage("Error");
					}
				} else {
					rsp.setData("Phone Number Incorrect!");
					rsp.setResponseCode(500);
					rsp.setResponseMessage("Error");
				}

			} catch (Exception e) {
				e.printStackTrace();

				rsp.setData("Connection Error!");
				rsp.setResponseCode(500);
				rsp.setResponseMessage("Error");
			}

		} catch (Exception e) {
			e.printStackTrace();

			rsp.setData("Driver Class Error");
			rsp.setResponseCode(500);
			rsp.setResponseMessage("Error");

		}

		return rsp;

	}

	@SuppressWarnings("unchecked")
	public Response getUser() {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			String selectQuery = "select * from user_details;";

			try (Connection conn = DriverManager.getConnection(url, username, pwd);
					Statement st = conn.createStatement();
					ResultSet rs = st.executeQuery(selectQuery);) {

				JSONArray jsonArray = new JSONArray();

				while (rs.next()) {
					JSONObject jsonObject = new JSONObject();
					jsonObject.put("sNo", rs.getString("s_no"));
					jsonObject.put("firstName", rs.getString("first_name"));
					jsonObject.put("lastName", rs.getString("last_name"));
					jsonObject.put("dateOfBirth", rs.getDate("date_of_birth"));
					jsonObject.put("gender", rs.getString("gender"));
					jsonObject.put("phoneNo", rs.getLong("phone_no"));
					jsonObject.put("email", rs.getString("email"));
					jsonObject.put("createdBy", rs.getString("created_by"));
					jsonObject.put("createdDate", rs.getDate("created_date"));

					jsonArray.add(jsonObject);

				}

				rsp.setData("User Fetched Successfully");
				rsp.setResponseCode(200);
				rsp.setResponseMessage("Success");
				rsp.setJdata(jsonArray);

			} catch (Exception e) {
				e.printStackTrace();

				rsp.setData("Cannot Fetch User!");
				rsp.setResponseCode(500);
				rsp.setResponseMessage("Error");
			}
		} catch (Exception e) {
			e.printStackTrace();

			rsp.setData("Driver Class Error");
			rsp.setResponseCode(500);
			rsp.setResponseMessage("Error");
		}

		return rsp;
	}

	@SuppressWarnings("unchecked")
	public Response getOneUser(String sno) {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			String selectQuery = "select * from user_details where s_no='" + sno + "';";
			try (Connection conn = DriverManager.getConnection(url, username, pwd);
					Statement st = conn.createStatement();
					ResultSet rs = st.executeQuery(selectQuery);) {

				JSONObject jsonObject = new JSONObject();

				while (rs.next()) {

					jsonObject.put("sNo", rs.getString("s_no"));
					jsonObject.put("firstName", rs.getString("first_name"));
					jsonObject.put("lastName", rs.getString("last_name"));
					jsonObject.put("dateOfBirth", rs.getDate("date_of_birth"));
					jsonObject.put("gender", rs.getString("gender"));
					jsonObject.put("phoneNo", rs.getLong("phone_no"));
					jsonObject.put("email", rs.getString("email"));
					jsonObject.put("createdBy", rs.getString("created_by"));
					jsonObject.put("createdDate", rs.getDate("created_date"));
				}
				rsp.setData("User Fetched Successfully");
				rsp.setResponseCode(200);
				rsp.setResponseMessage("Success");
				rsp.setJdata(jsonObject);
			}

			catch (Exception e) {
				e.printStackTrace();

				rsp.setData("Cannot Fetch User!");
				rsp.setResponseCode(500);
				rsp.setResponseMessage("Error");

			}
		} catch (Exception e) {
			e.printStackTrace();

			rsp.setData("Driver Class Error");
			rsp.setResponseCode(500);
			rsp.setResponseMessage("Error");

		}

		return rsp;
	}

	public Response updateEmail(SignUpModel values) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			String updateEmailQuery = "UPDATE kgm.user_details SET email = '" + values.getEmail() + "' WHERE s_no = '"
					+ values.getsNo() + "' ;";

			try (Connection conn = DriverManager.getConnection(url, username, pwd);
					Statement st = conn.createStatement();) {

				st.executeUpdate(updateEmailQuery);

				rsp.setData("Success");
				rsp.setResponseCode(200);
				rsp.setResponseMessage("Your Email has been updated!");

			} catch (Exception e) {
				e.printStackTrace();

				rsp.setData("Cannot Fetch User!");
				rsp.setResponseCode(500);
				rsp.setResponseMessage("Error");
			}

		} catch (Exception e) {
			e.printStackTrace();

			rsp.setData("Driver Class Error");
			rsp.setResponseCode(500);
			rsp.setResponseMessage("Error");
		}

		return rsp;
	}

	public Response deleteUser(String sno) {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			try (Connection conn = DriverManager.getConnection(url, username, pwd);
					Statement st = conn.createStatement();) {

				String deleteUser = "DELETE FROM kgm.user_details WHERE s_no = '" + sno + "';";

				st.executeUpdate(deleteUser);

				rsp.setData("Success");
				rsp.setResponseCode(200);
				rsp.setResponseMessage("User has been Updated!");

			} catch (Exception e) {
				e.printStackTrace();

				rsp.setData("Cannot Fetch User!");
				rsp.setResponseCode(500);
				rsp.setResponseMessage("Error");
			}

		} catch (Exception e) {
			e.printStackTrace();

			rsp.setData("Driver Class Error");
			rsp.setResponseCode(500);
			rsp.setResponseMessage("Error");

		}

		return rsp;
	}

	public Response userLogin(String email, String password) {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			String loginStatement = "Select * from kgm.user_details where email = '" + email + "' and password = '"
					+ password + "';";

			try (Connection conn = DriverManager.getConnection(url, username, pwd);
					PreparedStatement pst = conn.prepareStatement(loginStatement);
					ResultSet rs = pst.executeQuery();) {

				String result;

				if (rs.next()) {
					result = "Existing User";
				} else {
					result = "No user Found";
				}
				rsp.setData(result);

			} catch (Exception e) {
				e.printStackTrace();

			}
		} catch (Exception e) {
			e.printStackTrace();

			rsp.setData("Driver Class Error");
			rsp.setResponseCode(500);
			rsp.setResponseMessage("Error");

		}

		return rsp;
	}

	public Response softDelete(String sno, boolean isactive) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			try (Connection conn = DriverManager.getConnection(url, username, pwd);
					Statement st = conn.createStatement();) {

				String softDelete = "DELETE FROM kgm.user_details WHERE sno = '" + sno + "' and isactive='" + false
						+ "';";

				st.executeUpdate(softDelete);

				rsp.setData("Success");
				rsp.setResponseCode(200);
				rsp.setResponseMessage("User has been Updated!");

			} catch (Exception e) {
				e.printStackTrace();

				rsp.setData("Cannot Fetch User!");
				rsp.setResponseCode(500);
				rsp.setResponseMessage("Error");
			}

		} catch (Exception e) {
			e.printStackTrace();

			rsp.setData("Driver Class Error");
			rsp.setResponseCode(500);
			rsp.setResponseMessage("Error");
		}
		return rsp;
	}

	public Response updateValues(SignUpModel values) {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			String updateEmailQuery = "UPDATE kgm.user_details SET email = '" + values.getEmail() + "', phone_no = '" + values.getPhoneNo() + "', password = '" + values.getPassword() + "' WHERE s_no = '"+ values.getsNo() + "' ;";

			try (Connection conn = DriverManager.getConnection(url, username, pwd);
					Statement st = conn.createStatement();) {

				if (String.valueOf(values.getPhoneNo()).length() == 10 && values.getPhoneNo() >= 6000000000l
						&& values.getPhoneNo() <= 9999999999l) {

					String email = "^[a-zA-Z0-9._%+-]+@gmail.com$";
					Pattern emailPattern = Pattern.compile(email);
					Matcher emailMatch = emailPattern.matcher(values.getEmail());

					if (emailMatch.matches() == true) {

						st.executeUpdate(updateEmailQuery);

						rsp.setData("Success");
						rsp.setResponseCode(200);
						rsp.setResponseMessage("Profile Updated Successfully!");

					} else {
						rsp.setData("Email Incorrect!");
						rsp.setResponseCode(500);
						rsp.setResponseMessage("Error");
					}
				} else {
					rsp.setData("Phone Number Incorrect!");
					rsp.setResponseCode(500);
					rsp.setResponseMessage("Error");
				}

			} catch (Exception e) {
				e.printStackTrace();

				rsp.setData("Invalid, Kindly Check Your Data!");
				rsp.setResponseCode(500);
				rsp.setResponseMessage("Error");
			}

		} catch (Exception e) {
			e.printStackTrace();

			rsp.setData("Driver Class Error");
			rsp.setResponseCode(500);
			rsp.setResponseMessage("Error");
		}

		return rsp;
	}

	public Response updateAllValues(SignUpModel values) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			String insertQuery = "UPDATE kgm.user_details SET first_name = '" + values.getFirstName()
					+ "',last_name = '" + values.getLastName() + "',date_of_birth = '" + values.getDateOfBirth()
					+ "',gender = '" + values.getGender() + "',phone_no = '" + values.getPhoneNo() + "',email = '"
					+ values.getEmail() + "',password = '" + values.getPassword() + "',created_by = '"
					+ values.getCreatedBy() + "',updated_by = '" + values.getUpdatedBy() + "' WHERE s_no = '"
					+ values.getsNo() + "' ;";

			try (Connection conn = DriverManager.getConnection(url, username, pwd);
					Statement st = conn.createStatement();) {

				if (String.valueOf(values.getPhoneNo()).length() == 10 && values.getPhoneNo() >= 6000000000l
						&& values.getPhoneNo() <= 9999999999l) {

					String email = "^(.+)@gmail.com$";
					Pattern emailPattern = Pattern.compile(email);
					Matcher emailMatch = emailPattern.matcher(values.getEmail());

					if (emailMatch.matches() == true) {

						st.executeUpdate(insertQuery);

						rsp.setData("Success");
						rsp.setResponseCode(200);
						rsp.setResponseMessage("Your Data has been updated!");

					} else {
						rsp.setData("Email Incorrect!");
						rsp.setResponseCode(500);
						rsp.setResponseMessage("Error");

					}
				} else {
					rsp.setData("Phone Number Incorrect!");
					rsp.setResponseCode(500);
					rsp.setResponseMessage("Error");
				}
			} catch (Exception e) {
				e.printStackTrace();

				rsp.setData("Cannot Fetch User!");
				rsp.setResponseCode(500);
				rsp.setResponseMessage("Error");
			}

		} catch (Exception e) {
			e.printStackTrace();

			rsp.setData("Driver Class Error");
			rsp.setResponseCode(500);
			rsp.setResponseMessage("Error");
		}

		return rsp;
	}

	public Response sendOTP(String toEmail) {
		@Autowired
		private JavaMailSender javaMailSender;

		@Override
		public Response sendOTP(String toEmail) {

			Random random = new Random();
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < 4; i++) {
				sb.append(random.nextInt(10));
			}
			String otp = sb.toString();
			System.out.println("OTP" + otp);

			try {

				String fromEmail = "manashamanu0904@gmail.com";
				String sendText = "Greetings, \n \t Thanks for registering, your otp is " + otp
						+ ". Have a good day! \n\n\n Thanks & regards, \n Manasha R";
				String sendSubject = "OTP From Manasha R";

				SimpleMailMessage message = new SimpleMailMessage();
				message.setFrom(fromEmail);
				message.setTo(toEmail);
				message.setText(sendText);
				message.setSubject(sendSubject);

//				System.out.println("The Message Is : " + message);

				javaMailSender.send(message);

//				System.out.println("The Mail Sender Is : " + mailSender);

				rsp.setRespondCode(200);
				rsp.setRespondMsg("success");
				rsp.setjData(otp);
			} catch (Exception e) {
				e.printStackTrace();
				rsp.setRespondCode(500);
				rsp.setRespondMsg("failure");
			}
			return rsp;
		}
		
		return rsp;
	}

}