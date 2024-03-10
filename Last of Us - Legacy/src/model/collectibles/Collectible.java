package model.collectibles;

import exceptions.*;
import model.characters.Hero;

public interface Collectible {
public void pickUp(Hero h);
public void use(Hero h) throws NoAvailableResourcesException, InvalidTargetException;
}
