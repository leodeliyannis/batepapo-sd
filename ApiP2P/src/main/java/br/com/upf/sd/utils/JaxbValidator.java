package br.com.upf.sd.utils;

import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlElement;

public final class JaxbValidator {
    
    private static Logger logger = Logger.getLogger(JaxbValidator.class.getName());
    private static String MSG_PREENCHIMENTO_OBRIGATORIO = "Obrigatório o preenchimento do campo '%s'!";

    /**
     * Classe para validar se os campos obrigatórios estão preenchidos
     * @param <T>
     * @param target objeto que deve ser verificado
     * @param targetClass Classe do objeto que deve verificar as annotations
     * @throws ValidationException
     */
    public static <T> void validateRequired(final T target, final Class<T> targetClass) throws ValidationException {
        
    	StringBuilder errors = new StringBuilder();
        Field[] fields = targetClass.getDeclaredFields();
        
        for (Field field : fields) {
            XmlElement annotation = field.getAnnotation(XmlElement.class);
            if (annotation != null && annotation.required()) {
                try {
                    field.setAccessible(true);
                    
                    if ((field.get(target) == null) || 
                        (field.getType().getName().equals("java.lang.String") && ((String)field.get(target)).trim().equals("")) ||
                        (field.getType().getName().equals("java.lang.Integer") && ((Integer)field.get(target)) == 0)
                    )
                    {
                        if (errors.length() != 0) {
                            errors.append(" ");
                        }
                        
                        String message = String.format(MSG_PREENCHIMENTO_OBRIGATORIO, field.getName());
                        
                        logger.log(Level.WARNING, message);
                        errors.append(message);
                    
                    }
                        
                } catch (IllegalArgumentException e) {
                    logger.log(Level.SEVERE, field.getName()+" - " +e.getMessage(), e);
                } catch (IllegalAccessException e) {
                    logger.log(Level.SEVERE, field.getName()+" - " +e.getMessage(), e);
                }
            }
        }
        if (errors.length() != 0) {
            throw new ValidationException(errors.toString());
        }
    }
    
    /**
     * Classe para validar se os campos obrigat�rios est�o preenchidos
     * @param <T>
     * @param target objeto que deve ser verificado
     * @param targetClass Classe do objeto que deve verificar as annotations
     * @throws ValidationException
     */
    public static <T> void validateNotNull(final T target, final Class<T> targetClass) throws ValidationException {
        
    	StringBuilder errors = new StringBuilder();
        Field[] fields = targetClass.getDeclaredFields();
        
        for (Field field : fields) {
            NotNull annotation = field.getAnnotation(NotNull.class);
            if (annotation != null) {
                try {
                    field.setAccessible(true);
                    
                    if ((field.get(target) == null) || 
                        (field.getType().getName().equals("java.lang.String") && ((String)field.get(target)).trim().equals(""))                   
                    )
                    {
                        if (errors.length() != 0) {
                            errors.append(" ");
                        }
                        
                        String message = String.format(MSG_PREENCHIMENTO_OBRIGATORIO, field.getName());
                        
                        logger.log(Level.WARNING, message);
                        errors.append(message);
                    
                    }
                        
                } catch (IllegalArgumentException e) {
                    logger.log(Level.SEVERE, field.getName()+" - " +e.getMessage(), e);
                } catch (IllegalAccessException e) {
                    logger.log(Level.SEVERE, field.getName()+" - " +e.getMessage(), e);
                }
            }
        }
        if (errors.length() != 0) {
            throw new ValidationException(errors.toString());
        }
    }
}