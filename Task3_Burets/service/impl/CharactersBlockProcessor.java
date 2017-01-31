package by.training.xml_analyzer.service.impl;

import by.training.equipment_store.service.exception.ServiceException;
import by.training.xml_analyzer.dao.impl.FileCharacterStreamImpl;
import by.training.xml_analyzer.util.exception.UtilException;

class CharactersBlockProcessor {
    public String createString(FileCharacterStreamImpl stream, char element) throws ServiceException {
        StringBuilder buffer = new StringBuilder(0);

        try {
            int currentElement;
            do {
                currentElement = stream.getNext();
                buffer.append((char) currentElement);
            } while (!isEndStateCharacter(currentElement));
            
            buffer.insert(0, element);

            return buffer.toString();
        } catch (UtilException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    private boolean isEndStateCharacter(int element) {
        return element == -1 || (char) element == '>' || (char) element == '<';
    }
}

