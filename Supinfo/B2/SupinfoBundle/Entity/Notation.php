<?php

namespace Nts\SupinfoBundle\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * Nts\SupinfoBundle\Entity\Notation
 *
 * @ORM\Table()
 * @ORM\Entity(repositoryClass="Nts\SupinfoBundle\Entity\NotationRepository")
 */
class Notation
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
     * @var integer $intervention_id
     *
     * @ORM\Column(name="intervention_id", type="integer")
     */
    private $intervention_id;

    /**
     * @var integer $knowledge
     *
     * @ORM\Column(name="knowledge", type="integer")
     */
    private $knowledge;

    /**
     * @var integer $teaching
     *
     * @ORM\Column(name="teaching", type="integer")
     */
    private $teaching;

    /**
     * @var integer $answers
     *
     * @ORM\Column(name="answers", type="integer")
     */
    private $answers;

    /**
     * @var integer $content
     *
     * @ORM\Column(name="content", type="integer")
     */
    private $content;

    /**
     * @var integer $format
     *
     * @ORM\Column(name="format", type="integer")
     */
    private $format;

    /**
     * @var integer $examples
     *
     * @ORM\Column(name="examples", type="integer")
     */
    private $examples;

    /**
     * @var string $comments
     *
     * @ORM\Column(name="comments", type="string", length=255)
     */
    private $comments;


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
     * Set intervention_id
     *
     * @param integer $interventionId
     */
    public function setInterventionId($interventionId)
    {
        $this->intervention_id = $interventionId;
    }

    /**
     * Get intervention_id
     *
     * @return integer 
     */
    public function getInterventionId()
    {
        return $this->intervention_id;
    }

    /**
     * Set knowledge
     *
     * @param integer $knowledge
     */
    public function setKnowledge($knowledge)
    {
        $this->knowledge = $knowledge;
    }

    /**
     * Get knowledge
     *
     * @return integer 
     */
    public function getKnowledge()
    {
        return $this->knowledge;
    }

    /**
     * Set teaching
     *
     * @param integer $teaching
     */
    public function setTeaching($teaching)
    {
        $this->teaching = $teaching;
    }

    /**
     * Get teaching
     *
     * @return integer 
     */
    public function getTeaching()
    {
        return $this->teaching;
    }

    /**
     * Set answers
     *
     * @param integer $answers
     */
    public function setAnswers($answers)
    {
        $this->answers = $answers;
    }

    /**
     * Get answers
     *
     * @return integer 
     */
    public function getAnswers()
    {
        return $this->answers;
    }

    /**
     * Set content
     *
     * @param integer $content
     */
    public function setContent($content)
    {
        $this->content = $content;
    }

    /**
     * Get content
     *
     * @return integer 
     */
    public function getContent()
    {
        return $this->content;
    }

    /**
     * Set format
     *
     * @param integer $format
     */
    public function setFormat($format)
    {
        $this->format = $format;
    }

    /**
     * Get format
     *
     * @return integer 
     */
    public function getFormat()
    {
        return $this->format;
    }

    /**
     * Set examples
     *
     * @param integer $examples
     */
    public function setExamples($examples)
    {
        $this->examples = $examples;
    }

    /**
     * Get examples
     *
     * @return integer 
     */
    public function getExamples()
    {
        return $this->examples;
    }

    /**
     * Set comments
     *
     * @param string $comments
     */
    public function setComments($comments)
    {
        $this->comments = $comments;
    }

    /**
     * Get comments
     *
     * @return string 
     */
    public function getComments()
    {
        return $this->comments;
    }
}