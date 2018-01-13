/*
 * Copyright 2014 Raul Vera <twyblade64@hotmail.com>.
 *
 * This work is licensed under the
 * Creative Commons Attribution-NonCommercial-NoDerivs 3.0 Unported License.
 * To view a copy of this license, visit
 *
 *      http://creativecommons.org/licenses/by-nc-nd/3.0/
 *
 * or send a letter to Creative Commons, 444 Castro Street, Suite 900,
 * Mountain View, California, 94041, USA.
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package TermGraph;

import java.awt.geom.Point2D;

/**
 *
 * @author Raul Vera <twyblade64@hotmail.com>
 */
public class Vector2D {
    public double m_x;
    public double m_y;

    public Vector2D() {
        Init(0,0);
    }

    public Vector2D( double x, double y) {
        Init( x, y );
    }

    /**
     * Inicializa vector
     * @param x
     * @param y
     */
    public final void Init( double x, double y ) {
        m_x = x;
        m_y = y;
    }

    /**
     * Suma otro vector a este vector
     * @param v
     * @return
     */
    public Vector2D Add( Vector2D v ) {
        m_x += v.m_x;
        m_y += v.m_y;

        return this;
    }


    /**
     * Suma este y otro vector a un nuevo vector
     * @param v
     * @return
     */
    public Vector2D AddNew( Vector2D v ) {
        return new Vector2D(m_x + v.m_x, m_y + v.m_y);
    }

    /**
     * Resta otro vector a este vector
     * @param v
     * @return
     */
    public Vector2D Sub( Vector2D v ) {
        m_x -= v.m_x;
        m_y -= v.m_y;

        return this;
    }

    /**
     * Resta este y otro vector a un nuevo vector
     * @param v
     * @return
     */
    public Vector2D SubNew( Vector2D v ) {
        return new Vector2D(m_x - v.m_x, m_y - v.m_y);
    }

    /**
     * Multiplica este vector con otro vector
     * @param v
     * @return
     */
    public Vector2D Mul( Vector2D v ) {
        m_x *= v.m_x;
        m_y *= v.m_y;

        return this;
    }

    /**
     * Multiplica este vector con otro vector a un nuevo vector
     * @param v
     * @return
     */
    public Vector2D MulNew( Vector2D v ) {
        return new Vector2D(m_x * v.m_x, m_y * v.m_y);
    }

    /**
     * Divide este vector con otro vector
     * @param v
     * @return
     */
    public Vector2D Div( Vector2D v ) {
        m_x /= v.m_x;
        m_y /= v.m_y;

        return this;
    }

    /**
     * Divide este vector con otro vector a un nuevo vector
     * @param v
     * @return
     */
    public Vector2D DivNew( Vector2D v ) {
        return new Vector2D(m_x / v.m_x, m_y / v.m_y);
    }

    /**
     * Suma un valor X a este vector
     * @param x
     * @return
     */
    public Vector2D AddX( double x ) {
        m_x += x;

        return this;
    }

    /**
     * Suma este vector y un valor X a un nuevo vector
     * @param x
     * @return
     */
    public Vector2D AddXNew( double x ) {
        return new Vector2D( m_x+x , m_y );
    }

    /**
     * Resta un valor X a este vector
     * @param x
     * @return
     */
    public Vector2D SubX( double x ) {
        m_x -= x;
        return this;
    }

    /**
     * Resta este vector y un valor X a un nuevo vector
     * @param x
     * @return
     */
    public Vector2D SubXNew( double x ) {
        return new Vector2D( m_x-x , m_y );
    }

    /**
     * Suma un valor Y a este vector
     * @param y
     * @return
     */
    public Vector2D AddY( double y ) {
        m_y += y;

        return this;
    }

    /**
     * Suma este vector y un valor Y a un nuevo vector
     * @param y
     * @return
     */
    public Vector2D AddYNew( double y ) {
        return new Vector2D( m_x , m_y+y );
    }

    /**
     * Resta un valor Y a este vector
     * @param y
     * @return
     */
    public Vector2D SubY( double y ) {
        m_y -= y;
        return this;
    }

    /**
     * Resta este vector y un valor Y a un nuevo vector
     * @param y
     * @return
     */
    public Vector2D SubYNew( double y ) {
        return new Vector2D( m_x , m_y-y );
    }

    /**
     * Suma este vector con un escalar
     * @param s
     * @return
     */
    public Vector2D AddScalar( double s ) {
        m_x += s;
        m_y += s;

        return this;
    }

    /**
     * Suma este vector con un escalar a un nuevo vector
     * @param s
     * @return
     */
    public Vector2D AddScalarNew( double s ) {
        return new Vector2D(m_x + s, m_y + s);
    }

    /**
     * Resta este vector con un escalar
     * @param s
     * @return
     */
    public Vector2D SubScalar( double s ) {
        m_x -= s;
        m_y -= s;

        return this;
    }

    /**
     * Resta este vector con un escalar a un nuevo vector
     * @param s
     * @return
     */
    public Vector2D SubScalarNew( double s ) {
        return new Vector2D(m_x + s, m_y + s);
    }

    /**
     * Multiplica este vector con un escalar
     * @param s
     * @return
     */
    public Vector2D MulScalar( double s ) {
        m_x *= s;
        m_y *= s;

        return this;
    }

    /**
     * Multiplica este vector con un escalar a un nuevo vector
     * @param s
     * @return
     */
    public Vector2D MulScalarNew( double s ) {
        return new Vector2D(m_x * s, m_y * s);
    }

    /**
     * Divide este vector con un escalar
     * @param s
     * @return
     */
    public Vector2D DivScalar( double s ) {
        m_x /= s;
        m_y /= s;

        return this;
    }

    /**
     * Divide este vector con un escalar a un nuevo vector
     * @param s
     * @return
     */
    public Vector2D DivScalarNew( double s ) {
        return new Vector2D(m_x / s, m_y / s);
    }

    public double CrossZ(Vector2D v ) {
        return m_x * v.m_y - m_y * v.m_x;
    }

    /**
     * Producto punto entre este y otro vector
     * @param v
     * @return
     */
    public double Dot( Vector2D v ) {
        return m_x * v.m_x + m_y * v.m_y;
    }

    /**
     * Magnitud cuadrada del vector
     * @return x^2 + y^2
     */
    public double getLengthSqr() {
        return Dot(this);
    }

    /**
     * Magnitud del vector
     * @return sqrt(x^2 + y^2)
     */
    public double getLength() {
        return Math.sqrt(getLengthSqr());
    }

    /**
     * Normalización del vector
     * @return
     */
    public Vector2D Normalize() {
        MulScalar(1.0/getLength());
        return this;
    }

    /**
     * Normalización a nuevo vector
     * @return
     */
    public Vector2D NormalizeNew() {
        return MulScalarNew(1.0/getLength());
    }

    /**
     * Valor absoluto del vector
     * @return
     */
    public Vector2D Abs() {
        m_x = Math.abs(m_x);
        m_y = Math.abs(m_y);
        return this;
    }

    /**
     * Valor absoluto del vector a nuevo vector
     * @return
     */
    public Vector2D AbsNew() {
        return new Vector2D(Math.abs(m_x), Math.abs(m_y));
    }

    /**
     * Redondeo hacia abajo del vector a nuevo vector
     * @return
     */
    public Vector2D FloorNew() {
        return new Vector2D(Math.floor(m_x), Math.floor(m_y));
    }

    /**
     * Ajusta el vector a un área rectangular y lo regresa a nuevo vector
     * @param min Vector con los coordenadas minimas de rectangulo
     * @param max Vector con las coordenadas máximas de rectangulo
     * @return Vector2 con localización en el punto de ajuste
     */
    public Vector2D ClampNew(Vector2D min, Vector2D max) {
        return new Vector2D (
            Math.max( Math.min(m_x, max.m_x), min.m_x),
            Math.max( Math.min(m_y, max.m_y), min.m_y)
        );
    }

    /**
     * Vector perpendicular al vector actual de la forma (-y,x)
     * @return Vector2 (-y,x)
     */
    public Vector2D Perp() {
        double t = m_x;
        m_x = -m_y;
        m_y = t;
        return this;
    }


    /**
     * Obten un vector perpendicular al vector actual de la forma (-y,x)
     * @return Vector2 (-y,x)
     */
    public Vector2D PerpNew() {
        return new Vector2D( -m_y , m_x);
    }

    /**
     * Invierte el vector
     * @return
     */
    public Vector2D Neg() {
        m_x = -m_x;
        m_y = -m_y;
        return this;
    }

    /**
     * Invierte el vector a un nuevo vector
     * @return Vector2 (-x,-y)
     */
    public Vector2D NegNew() {
        return new Vector2D(-m_x, -m_y);
    }

    /**
     * Es un vector igual a otro?
     * @param v
     * @return x1 == x2 && y1 == y2
     */
    public boolean Equal( Vector2D v) {
        return m_x == v.m_x && m_y ==v.m_y;
    }

    /**
     * Creación de un vector desde un ángulo
     * @param angle Angulo en radianes
     * @return Vector2 (Cos ang, Sin ang)
     */
    public static Vector2D FromAngle( double angle ) {
        return new Vector2D( Math.cos(angle) , Math.sin(angle));
    }

    /**
     *
     * @return
     */
    public double toAngle() {
        double angle = Math.atan2(m_y, m_x);
        if (angle < 0)
            angle += Math.PI*2;
        return angle;
    }

    /**
     *
     * @param point
     * @return
     */
    public static Vector2D fromPoint(Point2D point) {
        return new Vector2D(point.getX(), point.getY());
    }

    /**
     *
     * @return
     */
    public Point2D toPoint() {
        return new Point2D.Double(m_x, m_y);
    }

    /**
     *
     */
    public void Clear() {
        m_x = 0;
        m_y = 0;
    }

    /**
     *
     * @return
     */
    public Vector2D Clone() {
        return new Vector2D(m_x, m_y);
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "x="+m_x+",y="+m_y;
    }

    /**
     *
     * @param v
     * @return
     */
    public Vector2D CloneInto (Vector2D v) {
        m_x = v.m_x;
        m_y = v.m_y;
        return this;
    }

    /**
     *
     * @param angle
     * @return
     */
    public Vector2D Rotate(double angle) {
        double tx = m_x;
        m_x = tx*Math.cos(angle) - m_y*Math.sin(angle);
        m_y = tx*Math.sin(angle) + m_y*Math.cos(angle);
        return this;
    }

    /**
     *
     * @param v
     * @return
     */
    public Vector2D Rotate(Vector2D v) {
        double tx = m_x;
        m_x = tx*v.m_x - m_y*v.m_y;
        m_y = tx*v.m_y + m_y*v.m_x;
        return this;
    }

    /**
     *
     * @param v
     * @return
     */
    public Vector2D InvRotate(Vector2D v) {
        double tx = m_x;
        m_x = tx*v.m_x + m_y*v.m_y;
        m_y =-tx*v.m_y + m_y*v.m_x;
        return this;
    }

    public static Vector2D FromMin( Vector2D v1, Vector2D v2 ) {
        return new Vector2D(Math.min(v1.m_x, v2.m_x), Math.min(v1.m_y, v2.m_y));
    }

    public static Vector2D FromMax( Vector2D v1, Vector2D v2 ) {
        return new Vector2D(Math.max(v1.m_x, v2.m_x), Math.max(v1.m_y, v2.m_y));
    }

    public Vector2D Floor() {
        m_x = Math.floor(m_x);
        m_y = Math.floor(m_y);

        return this;
    }

    //---
    public Vector2D Clamp( Vector2D min, Vector2D  max ) {
        m_x = Math.max( Math.min(m_x, max.m_x), min.m_x);
        m_y = Math.max( Math.min(m_y, max.m_y), min.m_y);

        return this;
    }

    /*public Vector2D MulComplex(Vector2D v1, Vector2D v2) {
        return new Vector2D(
                v1.m_x*v2.m_x - v1.m_y*v2.m_y,
                v1.m_x*v2.m_y + v1.m_y*v2.m_x
        );
    }*/
    // x1*x2 + i(x1*y2) + i(x2*y1) - y1*y2
}
