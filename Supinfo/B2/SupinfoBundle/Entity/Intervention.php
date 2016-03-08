<?php

namespace Nts\SupinfoBundle\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * Nts\SupinfoBundle\Entity\Intervention
 *
 * @ORM\Table()
 * @ORM\Entity(repositoryClass="Nts\SupinfoBundle\Entity\InterventionRepository")
 */
class Intervention
{
    /**
     * @var integer $id
     *
     * @ORM\Column(name="id", type="integer")
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="AUTO")
     */
    private $id;

    /**
     * @var string $subject
     *
     * @ORM\Column(name="subject", type="string", length=255)
     */
    private $subject;

    /**
     * @var datetime $start
     *
     * @ORM\Column(name="start", type="datetime")
     */
    private $start;

    /**
     * @var datetime $end
     *
     * @ORM\Column(name="end", type="datetime")
     */
    private $end;

    /**
     * @var string $status
     *
     * @ORM\Column(name="status", type="string", length=255)
     */
    private $status;

    /**
     * @var integer $sta_id
     *
     * @ORM\Column(name="sta_id", type="integer")
     */
    private $sta_id;

    /**
     * @var integer $campus_id
     *
     * @ORM\Column(name="campus_id", type="integer")
     */
    private $campus_id;

    /**
     * @var boolean $active
     *
     * @ORM\Column(name="active", type="boolean")
     */
    private $active;


    /**
     * Get id
     *
     * @return integer 
     */
    public function getId()
    {
        return $this->id;
    }

    /**
     * Set subject
     *
     * @param string $subject
     */
    public function setSubject($subject)
    {
        $this->subject = $subject;
    }

    /**
     * Get subject
     *
     * @return string 
     */
    public function getSubject()
    {
        return $this->subject;
    }

    /**
     * Set start
     *
     * @param datetime $start
     */
    public function setStart($start)
    {
        $this->start = $start;
    }

    /**
     * Get start
     *
     * @return datetime 
     */
    public function getStart()
    {
        return $this->start;
    }

    /**
     * Set end
     *
     * @param datetime $end
     */
    public function setEnd($end)
    {
        $this->end = $end;
    }

    /**
     * Get end
     *
     * @return datetime 
     */
    public function getEnd()
    {
        return $this->end;
    }

    /**
     * Set status
     *
     * @param string $status
     */
    public function setStatus($status)
    {
        $this->status = $status;
    }

    /**
     * Get status
     *
     * @return string 
     */
    public function getStatus()
    {
        return $this->status;
    }

    /**
     * Set sta_id
     *
     * @param integer $staId
     */
    public function setStaId($staId)
    {
        $this->sta_id = $staId;
    }

    /**
     * Get sta_id
     *
     * @return integer 
     */
    public function getStaId()
    {
        return $this->sta_id;
    }

    /**
     * Set campus_id
     *
     * @param integer $campusId
     */
    public function setCampusId($campusId)
    {
        $this->campus_id = $campusId;
    }

    /**
     * Get campus_id
     *
     * @return integer 
     */
    public function getCampusId()
    {
        return $this->campus_id;
    }

    /**
     * Set active
     *
     * @param boolean $active
     */
    public function setActive($active)
    {
        $this->active = $active;
    }

    /**
     * Get active
     *
     * @return boolean 
     */
    public function getActive()
    {
        return $this->active;
    }
}