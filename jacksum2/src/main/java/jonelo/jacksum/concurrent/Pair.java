/*
 * Copyright (C) 2015 Federico Tello Gentile <federicotg@gmail.com>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 */
package jonelo.jacksum.concurrent;

import java.util.Objects;

/**
 *
 * @author Federico Tello Gentile <federicotg@gmail.com>
 */
public class Pair<First, Second> {
    private First first;
    private Second second;
    
    public Pair(First first, Second second){
        this.first = first;
        this.second = second;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.first, this.second);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Pair
                && Objects.equals(this.first, ((Pair) obj).first)
                && Objects.equals(this.second, ((Pair) obj).second);
    }

    public First getFirst() {
        return first;
    }

    public Second getSecond() {
        return second;
    }
    
    
    
}
