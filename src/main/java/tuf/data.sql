
--SELECT DISTINCT r.*
--FROM reservation
--JOIN item ON i.item_id = r.reservation_id
--WHERE i.item_id = '001'
--ORDER BY r.reservation_id ASC

SELECT DISTINCT r.*
FROM reservation r
JOIN item i ON i.item_id = r.reservation_id
WHERE i.item_id = '001'
ORDER BY r.reservation_id ASC


@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query(
        value = """
            SELECT DISTINCT r.*
            FROM reservation r
            JOIN item i ON i.itemid = r.itemid
            WHERE i.itemid = :itemId
            ORDER BY r.reservationid ASC
            """,
        nativeQuery = true
    )
    List<Reservation> findDistinctReservationsByItemId(@Param("itemId") String itemId);

    @Query("SELECT DISTINCT r.*
    FROM reservation r
    JOIN item i ON i.item_id = r.reservation_id
    WHERE i.item_id = :itemId
    ORDER BY r.reservation_id ASC",nativeQuery = true)
}


SELECT DISTINCT a FROM Author a JOIN FETCH a.books


