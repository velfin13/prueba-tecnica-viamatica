package me.velfinvelasquez.utils;

public class ValidatorData {
	public boolean isValidIdentification(String identification) {
        for (int i = 0; i <= identification.length() - 4; i++) {
            char currentChar = identification.charAt(i);
            boolean hasRepeatedDigits = true;
            
            for (int j = i + 1; j < i + 4; j++) {
                if (identification.charAt(j) != currentChar) {
                    hasRepeatedDigits = false;
                    break;
                }
            }
            
            if (hasRepeatedDigits) {
                return false;
            }
        }
        
        return true;
    }
	
	public String generateEmail(String name, String lastname,String identificacion) {
		String firstNameInitial = name.substring(0, 1).toLowerCase();
		String lastName = lastname.split(" ")[0].toLowerCase();
		String identificationLastFourDigits = identificacion.substring(identificacion.length() - 4);

		String email = firstNameInitial + lastName + identificationLastFourDigits + "@mail.com";
		return email;
	}
}
