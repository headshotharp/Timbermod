/**
 * Timbermod
 * Copyright © 2021 gmasil.de
 *
 * This file is part of Timbermod.
 *
 * Timbermod is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Timbermod is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Timbermod. If not, see <https://www.gnu.org/licenses/>.
 */
package de.headshotharp.timbermod;

public class User {

    private String name;
    private boolean timber = true;

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean hasTimber() {
        return timber;
    }

    public void setTimber(boolean timber) {
        this.timber = timber;
    }

}
