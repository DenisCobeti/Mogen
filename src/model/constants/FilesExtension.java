/*
 * Copyright (C) 2019 Neblis
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package model.constants;

/**
 *
 * @author Neblis
 */
public enum FilesExtension {
    
    OSM(".osm"),
    SUMO_CFG(".sumocfg"),
    NETCONVERT(".net.xml"),
    ROUTE(".rou.xml");
    
    private final String extension;

    private FilesExtension(String extension) {
        this.extension = extension;
    }

    public String getCommand() {
        return extension;
    }
}
